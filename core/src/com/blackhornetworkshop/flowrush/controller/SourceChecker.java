package com.blackhornetworkshop.flowrush.controller;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.model.HexActor;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;

//Created by TScissors.

public class SourceChecker {

    private static SourceChecker instance;

    private HexActor actor;
    private Group group;
    private boolean[] sourceArrayMain;
    private ArrayList<HexActor> tempoHexArray = new ArrayList<>();
    private ArrayList<HexActor> doveArray = new ArrayList<>();

    private boolean doveIsOn;
    private int numReceiversOn = 0;

    public static SourceChecker getInstance(){
        if(instance == null) instance = new SourceChecker();
        return instance;
    }

    private SourceChecker(){}

    public void initialization(Group mapGroup) {
        group = mapGroup;
        clearFields();
        for (int x = 0; x < MapController.getSpecialActorsArraySize(); x++) {
            actor = MapController.getSpecialActorsArrayChildren(x);
            if (actor.getInclude() == 3) {
                doveArray.add(actor); //заполняем массив голубей
            }
        }
    }

    private void clearFields() {//при перезапуске очищаем все
        doveArray.clear();
        tempoHexArray.clear();
        sourceArrayMain = null;
        actor = null;
    }


    public void checkAndSetActor() {
        disconnectAll();

        for (int x = 0; x < MapController.getHexGroupSize(); x++) {
            actor = MapController.getHexGroupChildren(x);
            if (actor.getInclude() == 1) {
                sourceArrayMain = actor.getSourceArray();
                tempoHexArray.add(actor); //первый член в массиве
                for (int i = 0; i < tempoHexArray.size(); i++) { // переборка массива, в самой переборке постоянно массив пополняется пока не будут перебраны все подключенные клетки одна за одной

                    actor = tempoHexArray.get(i);
                    sourceArrayMain = actor.getSourceArray();

                    checkActor(actor);
                }
            }
        }

        if (doveIsOn) { // отдельно проверка голубей тем же принципом
            for (int x = 0; x < doveArray.size(); x++) {
                actor = doveArray.get(x);
                HexController.setPowerOn(actor);
                tempoHexArray.clear();
                sourceArrayMain = actor.getSourceArray();
                tempoHexArray.add(actor);

                try {
                    for (int i = 0; i < tempoHexArray.size(); i++) { // переборка массива, в самой переборке постоянно массив пополняется пока не будут перебраны все подключенные клетки одна за одной

                        actor = tempoHexArray.get(i);
                        sourceArrayMain = actor.getSourceArray();

                        checkActor(actor);
                    }

                } catch (Exception ex) {
                    ex.getStackTrace();
                }
            }
        }
        for (int x = 0; x < MapController.getSpecialActorsArraySize(); x++) { //тут и ниже проверка на победу
            actor = MapController.getSpecialActorsArrayChildren(x);
            if (actor.getInclude() == 2 && actor.isPowerOn()) {
                numReceiversOn += 1;
            }
        }
        if (MapController.getNumOfReceivers() == numReceiversOn) {
            GameScreen.getInputMultiplexer().removeProcessor(FlowRush.getInstance().getHexesStage());//отключает касание к тайлам пока не выскочил lvlCompleteActor
            for (int i = 0; i < MapController.getHexGroupSize(); i++) {
                HexController.setHexBackgroundOn(MapController.getHexGroupChildren(i));
            }

            Timer.instance().clear();
            Timer.schedule(new Timer.Task() { //ждем посде сборки перед переходом на след уровень
                @Override
                public void run() {
                    GameScreen.getInstance().levelComplete();
                }
            }, 0.5f);
        }
    }

    private void checkTool(String name, int side) { //чекает соседний для главного актер по переданному имени XY, добавляет подключенные актеры в ArrayList для проверки

        int side2 = side + 3; //сторона для проверки

        if (side2 == 6) { // исключаем "перепрыгивание"
            side2 = 0;
        } else if (side2 == 7) {
            side2 = 1;
        } else if (side2 == 8) {
            side2 = 2;
        }

        if (group.findActor(name) != null) {
            HexActor actorTempo = group.findActor(name);
            if (!actorTempo.isPowerOn()) {
                boolean[] sourceArrayTempo = actorTempo.getSourceArray();

                if (sourceArrayMain[side] && sourceArrayTempo[side2]) { //сравниваем две стороны основго актера и "соседа"
                    HexController.setPowerOn(actorTempo);
                    tempoHexArray.add(actorTempo);
                    if (actorTempo.getInclude() == 3) {
                        doveIsOn = true;
                    }
                }
            }
        }
    }

    private void checkActor(HexActor actor) { //проверяет конкретно оддного актера по всем соседним позициям

        int x, y;

        x = actor.getXIndex();
        y = actor.getYIndex();

        if (x == 0 || x % 2 == 0) {
            String nameTempo = "" + (x - 1) + "" + y; //позиция слева снизу (0)
            checkTool(nameTempo, 0);
            nameTempo = "" + (x - 1) + "" + (y + 1); // позиция сдева сверху (1)
            checkTool(nameTempo, 1);
            nameTempo = "" + x + "" + (y + 1); // позиция сверху (2)
            checkTool(nameTempo, 2);
            nameTempo = "" + (x + 1) + "" + (y + 1); // позиция справа сверху (3)
            checkTool(nameTempo, 3);
            nameTempo = "" + (x + 1) + "" + y; // позиция справа снизу(4)
            checkTool(nameTempo, 4);
            nameTempo = "" + x + "" + (y - 1); // позиция снизу (5)
            checkTool(nameTempo, 5);
        } else {
            String nameTempo = "" + (x - 1) + "" + (y - 1); //позиция слева снизу (0)
            checkTool(nameTempo, 0);
            nameTempo = "" + (x - 1) + "" + y; // позиция сдева сверху (1)
            checkTool(nameTempo, 1);
            nameTempo = "" + x + "" + (y + 1); // позиция сверху (2)
            checkTool(nameTempo, 2);
            nameTempo = "" + (x + 1) + "" + y; // позиция справа сверху (3)
            checkTool(nameTempo, 3);
            nameTempo = "" + (x + 1) + "" + (y - 1); // позиция справа снизу(4)
            checkTool(nameTempo, 4);
            nameTempo = "" + x + "" + (y - 1); // позиция снизу (5)
            checkTool(nameTempo, 5);
        }

    }

    private void disconnectAll() { //все отрубает
        tempoHexArray.clear();
        numReceiversOn = 0;
        for (int x = 0; x < MapController.getHexGroupSize(); x++) {
            actor = MapController.getHexGroupChildren(x);
            if (actor.getInclude() != 1) {
                HexController.setPowerOff(actor);
            }
        }
        doveIsOn = false;
    }
}