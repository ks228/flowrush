package com.blackhornetworkshop.flowrush.controller;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.model.HexActor;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;

//Created by TScissors.

public class SourceChecker {

    private static SourceChecker instance;

    private HexActor actor;
    private boolean[] sourceArrayMain;
    private ArrayList<HexActor> tempHexArray = new ArrayList<>();
    private ArrayList<HexActor> doveArray = new ArrayList<>();

    private boolean doveIsOn;
    private int numReceiversOn = 0;

    public static SourceChecker getInstance(){
        if(instance == null) instance = new SourceChecker();
        return instance;
    }

    private SourceChecker(){}

    public void initialization() {
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
        tempHexArray.clear();
        sourceArrayMain = null;
        actor = null;
    }


    public void update() {
        disconnectAll();

        for (int x = 0; x < MapController.getHexGroupSize(); x++) {
            actor = MapController.getHexGroupChildren(x);
            if (actor.getInclude() == 1) {
                sourceArrayMain = actor.getSourceArray();
                tempHexArray.add(actor); //первый член в массиве
                for (int i = 0; i < tempHexArray.size(); i++) { // переборка массива, в самой переборке постоянно массив пополняется пока не будут перебраны все подключенные клетки одна за одной

                    actor = tempHexArray.get(i);
                    sourceArrayMain = actor.getSourceArray();

                    checkActorNew(actor);
                }
            }
        }

        if (doveIsOn) { // отдельно проверка голубей тем же принципом
            for (int x = 0; x < doveArray.size(); x++) {
                actor = doveArray.get(x);
                HexController.setPowerOn(actor);
                tempHexArray.clear();
                sourceArrayMain = actor.getSourceArray();
                tempHexArray.add(actor);

                for (int i = 0; i < tempHexArray.size(); i++) { // переборка массива, в самой переборке постоянно массив пополняется пока не будут перебраны все подключенные клетки одна за одной

                    actor = tempHexArray.get(i);
                    sourceArrayMain = actor.getSourceArray();

                    checkActorNew(actor);
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

    private void checkActorNew(HexActor actor) { //проверяет конкретно оддного актера по всем соседним позициям

        int x, y;

        x = actor.getXIndex();
        y = actor.getYIndex();

        if (x == 0 || x % 2 == 0) {
            checkTool((x-1)*10+y, 0);//позиция слева снизу (0)
            checkTool((x-1)*10+y+1, 1);// позиция сдева сверху (1)
            checkTool(x*10+y+1, 2);// позиция сверху (2)
            checkTool((x+1)*10+y+1, 3);// позиция справа сверху (3)
            checkTool((x+1)*10+y, 4);// позиция справа снизу(4)
            checkTool(x*10+y-1, 5);// позиция снизу (5)
        } else {
            checkTool((x-1)*10+y-1, 0);
            checkTool((x-1)*10+y, 1);
            checkTool(x*10+y+1, 2);
            checkTool((x+1)*10+y, 3);
            checkTool((x+1)*10+y-1, 4);
            checkTool(x*10+y-1, 5);
        }

    }

    private void checkTool(int index, int side) { //чекает соседний для главного актер по переданному имени XY, добавляет подключенные актеры в ArrayList для проверки

        int side2 = side + 3; //сторона для проверки

        if (side2 == 6) { // исключаем "перепрыгивание"
            side2 = 0;
        } else if (side2 == 7) {
            side2 = 1;
        } else if (side2 == 8) {
            side2 = 2;
        }

        HexActor actorTempo = MapController.findActor(index);
        if (actorTempo != null) {
            if (!actorTempo.isPowerOn()) {
                boolean[] sourceArrayTempo = actorTempo.getSourceArray();

                if (sourceArrayMain[side] && sourceArrayTempo[side2]) { //сравниваем две стороны основго актера и "соседа"
                    HexController.setPowerOn(actorTempo);
                    tempHexArray.add(actorTempo);
                    if (actorTempo.getInclude() == 3) {
                        doveIsOn = true;
                    }
                }
            }
        }
    }

    private void disconnectAll() { //все отрубает
        tempHexArray.clear();
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