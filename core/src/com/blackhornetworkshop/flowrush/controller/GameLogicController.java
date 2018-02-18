package com.blackhornetworkshop.flowrush.controller;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.model.HexActor;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;

//Created by TScissors.

public class GameLogicController {

    private static GameLogicController instance;

    private HexActor actor;
    private boolean[] sourceArrayMain;
    private ArrayList<HexActor> tempHexArray = new ArrayList<>();
    private ArrayList<HexActor> doveArray = new ArrayList<>();

    private boolean doveIsOn;
    private int numReceiversOn = 0;

    public static GameLogicController getInstance(){
        if(instance == null) instance = new GameLogicController();
        return instance;
    }

    private GameLogicController(){}

    public void initialization() {
        FlowRush.logDebug("GameLogicController initialization() method called");
        clearFields();
        for (int x = 0; x < MapController.getSpecialActorsArraySize(); x++) {
            actor = MapController.getSpecialActorsArrayChildren(x);
            if (actor.getInclude() == 3) {
                doveArray.add(actor);
            }
        }
    }

    private void clearFields() {
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
                tempHexArray.add(actor);
                for (int i = 0; i < tempHexArray.size(); i++) {

                    actor = tempHexArray.get(i);
                    sourceArrayMain = actor.getSourceArray();

                    checkActor(actor);
                }
            }
        }

        if (doveIsOn) {
            for (int x = 0; x < doveArray.size(); x++) {
                actor = doveArray.get(x);
                HexController.setPowerOn(actor);
                tempHexArray.clear();
                sourceArrayMain = actor.getSourceArray();
                tempHexArray.add(actor);

                for (int i = 0; i < tempHexArray.size(); i++) {

                    actor = tempHexArray.get(i);
                    sourceArrayMain = actor.getSourceArray();

                    checkActor(actor);
                }
            }
        }
        for (int x = 0; x < MapController.getSpecialActorsArraySize(); x++) {
            actor = MapController.getSpecialActorsArrayChildren(x);
            if (actor.getInclude() == 2 && actor.isPowerOn()) {
                numReceiversOn += 1;
            }
        }
        if (MapController.getNumOfReceivers() == numReceiversOn) {
            GameScreen.getInputMultiplexer().removeProcessor(FlowRush.getInstance().getHexesStage());
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

    private void checkActor(HexActor actor) {

        int x, y;

        x = actor.getXIndex();
        y = actor.getYIndex();

        if (x == 0 || x % 2 == 0) {
            checkTool((x-1)*10+y, 0);//BOTTOM LEFT (0)
            checkTool((x-1)*10+y+1, 1);// TOP LEFT (1)
            checkTool(x*10+y+1, 2);// TOP (2)
            checkTool((x+1)*10+y+1, 3);// TOP RIGHT (3)
            checkTool((x+1)*10+y, 4);// BOTTOM RIGHT (4)
            checkTool(x*10+y-1, 5);// BOTTOM (5)
        } else {
            checkTool((x-1)*10+y-1, 0);
            checkTool((x-1)*10+y, 1);
            checkTool(x*10+y+1, 2);
            checkTool((x+1)*10+y, 3);
            checkTool((x+1)*10+y-1, 4);
            checkTool(x*10+y-1, 5);
        }

    }

    private void checkTool(int index, int side) {

        int side2 = side + 3;

        if (side2 == 6) {
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

                if (sourceArrayMain[side] && sourceArrayTempo[side2]) {
                    HexController.setPowerOn(actorTempo);
                    tempHexArray.add(actorTempo);
                    if (actorTempo.getInclude() == 3) {
                        doveIsOn = true;
                    }
                }
            }
        }
    }

    private void disconnectAll() {
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