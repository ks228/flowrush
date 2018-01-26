package com.blackhornetworkshop.flowrush.gameplay;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.screens.GameScreen;

//Created by TScissors. Класс для проверки соединения между гексами. основная логика игры находится тут

public class SourceChecker { //основная логика игры находится тут

    private GameScreen gameScreen;

    private TileActor actor;
    private Group group;
    private boolean[] sourceArrayMain;
    private ArrayList<TileActor> tempoHexArray = new ArrayList<TileActor>();
    private ArrayList<TileActor> doveArray = new ArrayList<TileActor>(); //для постоянного хранения голубей

    private boolean doveIsOn;
    private int numReceiversOn = 0;

    public void initialization(GameScreen gameScrn, Group mapGroup) {
        this.gameScreen = gameScrn;
        group = mapGroup;
        clearFields();
        for (int x = 0; x < gameScreen.specialActorsArray.size(); x++) {
            actor = gameScreen.specialActorsArray.get(x);
            if (actor.getInclude() == 3) {
                doveArray.add(actor); //заполняем массив голубей
            }
        }
    }

    private void clearFields() {//при перезапуске очищаем все нахрен
        doveArray.clear();
        tempoHexArray.clear();
        sourceArrayMain = null;
        actor = null;
    }


    public void checkAndSetActor() {
        disconnectAll();

        for (int x = 0; x < gameScreen.groupArray.size(); x++) {
            actor = gameScreen.groupArray.get(x);
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
                TileController.setPowerOn(actor, gameScreen.game.atlas);
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
        for (int x = 0; x < gameScreen.specialActorsArray.size(); x++) { //тут и ниже проверка на победу
            actor = gameScreen.specialActorsArray.get(x);
            if (actor.getInclude() == 2 && actor.isPowerOn()) {
                numReceiversOn += 1;
            }
        }
        if (gameScreen.numOfReceivers == numReceiversOn) {
            gameScreen.inputMultiplexer.removeProcessor(gameScreen.stage);//отключает касание к тайлам пока не выскочил lvlCompleteActor
            for (int i = 0; i < gameScreen.groupArray.size(); i++) {
                TileController.setHexbackTouchOn(gameScreen.groupArray.get(i), gameScreen.game.atlas);
            }

            Timer.instance().start();
            Timer.schedule(new Timer.Task() { //ждем посде сборки перед переходом на след уровень
                @Override
                public void run() {
                    gameScreen.levelComplete();
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
            TileActor actorTempo = group.findActor(name);
            if (actorTempo.isPowerOn() == false) {
                boolean[] sourceArrayTempo = actorTempo.getSourceArray();

                if (sourceArrayMain[side] == true && sourceArrayTempo[side2] == true) { //сравниваем две стороны основго актера и "соседа"
                    com.blackhornetworkshop.flowrush.gameplay.TileController.setPowerOn(actorTempo, gameScreen.game.atlas);
                    tempoHexArray.add(actorTempo);
                    if (actorTempo.getInclude() == 3) {
                        doveIsOn = true;
                    }
                }
            }
        }
    }

    public void checkActor(TileActor actor) { //проверяет конкретно оддного актера по всем соседним позициям

        int x, y;

        x = actor.getxIndex();
        y = actor.getyIndex();

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

    public void disconnectAll() { //все отрубает
        tempoHexArray.clear();
        numReceiversOn = 0;
        for (int x = 0; x < gameScreen.groupArray.size(); x++) {
            actor = gameScreen.groupArray.get(x);
            if (actor.getInclude() != 1) {
                com.blackhornetworkshop.flowrush.gameplay.TileController.setPowerOff(actor, gameScreen.iconWhite, gameScreen.game.atlas);
            }
        }
        doveIsOn = false;
    }
}