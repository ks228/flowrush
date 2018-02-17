package com.blackhornetworkshop.flowrush.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.model.FRConstants;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.model.ActorInfo;
import com.blackhornetworkshop.flowrush.model.HexActor;
import com.blackhornetworkshop.flowrush.model.listeners.HexActorListener;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;

import java.util.ArrayList;
import java.util.HashMap;

import static com.blackhornetworkshop.flowrush.model.FRConstants.BUTTON_SIZE;
import static com.blackhornetworkshop.flowrush.model.FRConstants.DOVE_OFF_ICON;
import static com.blackhornetworkshop.flowrush.model.FRConstants.POINT_OFF_ICON;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_WIDTH;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SOURCE_ICON;
import static com.blackhornetworkshop.flowrush.model.FRConstants.HEX;

//Created by TScissors.

public class MapController {

    private static MapController instance;

    private static float xPos = 0;
    private static float yPos = 0;

    private static float minCoord = SCREEN_HEIGHT;
    private static float maxCoord = 0;

    private static final Group mapGroup = new Group();
    private static final Group hexGroup = new Group();
    private static final ArrayList<HexActor> specialActors = new ArrayList<>();

    private static int numOfReceivers = 0;

    private static HashMap<Integer, HexActor> hexHashMap= new HashMap<>();

    static int getNumOfReceivers(){return numOfReceivers;}
    static int getHexGroupSize(){return hexGroup.getChildren().size;}
    static HexActor getHexGroupChildren(int x){return (HexActor) hexGroup.getChildren().get(x);}
    public static int getSpecialActorsArraySize(){return specialActors.size();}
    public static HexActor getSpecialActorsArrayChildren(int x){return specialActors.get(x);}
    public static MapController getInstance(){
        if(instance == null){
            instance = new MapController();
            FlowRush.logDebug("MapController is initialized. Return new instance");
        }else{
            FlowRush.logDebug("MapController is already initialized. Return existing instance");
        }
        return instance;
    }

    private MapController(){}

    public static Group getMapGroup(){
        return mapGroup;
    }

    public static void createNewMapGroup(ArrayList<ArrayList<ActorInfo>> list) {
        FlowRush.logDebug("MapController createNewMapGroup() method called");

        mapGroup.clear();
        hexGroup.clear();
        hexHashMap.clear();
        specialActors.clear();
        numOfReceivers = 0;

        minCoord = SCREEN_HEIGHT;
        maxCoord = 0;

        mapGroup.addActor(UIPool.getHexBackgroundActor());

        for (int x = 0; x < list.size(); x++) {
            for (int y = 0; y < list.get(0).size(); y++) {
                createActor(x, y, list);
            }
        }

        float coefficient = 0;
        boolean minus = true;
        for (int x = 0; x < list.size(); x++) {
            if (list.get(x).get(0).getIndex() != 0 && x % 2 != 0) {
                minus = false;
            }
        }

        mapGroup.setWidth(list.size() * FRConstants.HEX_WIDTH - (list.size() - 1) * (FRConstants.HEX_WIDTH / 4));
        mapGroup.setHeight(maxCoord - minCoord);

        float availHeight = (SCREEN_HEIGHT - (SCREEN_WIDTH / 10) - UIPool.getLevelNumberActor().getHeight() * 1.1f - BUTTON_SIZE * 1.1f);
        float zoom = (SCREEN_WIDTH - (SCREEN_WIDTH/ 10)) / mapGroup.getWidth();

        if (mapGroup.getHeight() * zoom > availHeight && zoom > availHeight / mapGroup.getHeight()) {
            zoom = availHeight / mapGroup.getHeight();
        }

        if (minus) {
            coefficient = FRConstants.HEX_HEIGHT * 0.5f * zoom;
        }
        mapGroup.setPosition((SCREEN_WIDTH - mapGroup.getWidth()) / 2, ((SCREEN_HEIGHT- (mapGroup.getHeight())) / 2) - coefficient);

        mapGroup.setOrigin(Align.center);
        mapGroup.setScale(zoom);

        mapGroup.addActor(hexGroup);
    }

    private static void createActor(int x, int y, ArrayList<ArrayList<ActorInfo>> list) {
        ActorInfo actorInfo = list.get(x).get(y);

        if (actorInfo.getIndex() != 0) {
            setPosXY(x, y);

            if (yPos < minCoord) {
                minCoord = yPos;
            }

            if (yPos + FRConstants.HEX_HEIGHT > maxCoord) {
                maxCoord = yPos + FRConstants.HEX_HEIGHT;
            }

            int indexSpriteOff = 0;
            int indexSpriteOn = 0;

            if (actorInfo.getIndex() < 13) {
                indexSpriteOff = actorInfo.getIndex();
                indexSpriteOn = actorInfo.getIndex() + 12;
            } else if (actorInfo.getIndex() > 12 & actorInfo.getIndex() < 25) {
                indexSpriteOn = actorInfo.getIndex();
                indexSpriteOff = actorInfo.getIndex() - 12;
            } else if (actorInfo.getIndex() > 24 & actorInfo.getIndex() < 38) {
                indexSpriteOff = actorInfo.getIndex();
                indexSpriteOn = actorInfo.getIndex() + 13;
            } else if (actorInfo.getIndex() > 37 & actorInfo.getIndex() < 51) {
                indexSpriteOn = actorInfo.getIndex();
                indexSpriteOff = actorInfo.getIndex() - 13;
            }

            Sprite spriteOff = FRAssetManager.getSprite(HEX+indexSpriteOff);
            Sprite spriteOn = FRAssetManager.getSprite(HEX+indexSpriteOn);

            HexActor actor = new HexActor(actorInfo.getIndex(), actorInfo.getInclude(), spriteOff, spriteOn, actorInfo.getPosition(), x, y, createSourceArray(actorInfo.getIndex()));

            for (int a = 0; a < actor.getRotatePosition(); a++) {
                actor.moveSources();
            }

            actor.addListener(new HexActorListener(actor));

            if (actor.getInclude() == 2) {
                MapController.numOfReceivers += 1;
            }

            actor.setRotation(actorInfo.getPosition() * (-60));

            actor.getName();
            actor.setBounds(xPos, yPos, FRConstants.HEX_WIDTH, FRConstants.HEX_HEIGHT);
            actor.setOrigin(Align.center);

            HexController.setHexbackTouchOff(actor);

            if (actor.getInclude() == 1) {
                actor.setIcon(FRAssetManager.getSprite(SOURCE_ICON));
            } else if (actor.getInclude() == 2) {
                actor.setIcon(FRAssetManager.getSprite(POINT_OFF_ICON));
            } else if (actor.getInclude() == 3) {
                actor.setIcon(FRAssetManager.getSprite(DOVE_OFF_ICON));
            }

            ScaleToAction scale = new ScaleToAction();
            scale.setScale(1, 1);
            scale.setDuration(0.25f);

            actor.setScale(0f, 0f);
            actor.addAction(scale);

            int index = x*10+y;
            hexHashMap.put(index, actor);
            hexGroup.addActor(actor);

            if (actor.getInclude() != 0) {
                specialActors.add(actor);
            }
        }
    }

    private static void setPosXY(int x, int y) {

        xPos = x * FRConstants.HEX_WIDTH - x * (FRConstants.HEX_WIDTH / 4);

        if (x % 2 == 0) {
            yPos = y * FRConstants.HEX_HEIGHT + FRConstants.HEX_HEIGHT / 2;
        } else {
            yPos = y * FRConstants.HEX_HEIGHT;
        }
    }

    static HexActor findActor(int index) {
        return hexHashMap.get(index);
    }

    private static boolean[] createSourceArray(int index) {
        int type = 0;
        if (index < 13) {
            type = index;
        } else if (index > 12 && index < 25) {
            type = index-12;
        } else if (index > 24 && index < 38) {
            type = index - 24;
        } else if (index > 37 && index < 51) {
            type = index - 37;
        }
        boolean[] sourceArray;
        switch(type){
            case 1:
                sourceArray  = new boolean[] {false,false,true,false,false,true}; // 1
                break;
            case 2:
                sourceArray  = new boolean[] {false,false,true,false,true,false}; // 2
                break;
            case 3:
                sourceArray  = new boolean[] {true,false,true,false,true,false}; // 3
                break;
            case 4:
                sourceArray  = new boolean[] {false,false,true,true,false,true}; // 4
                break;
            case 5:
                sourceArray  = new boolean[] {false,true,true,false,false,true}; // 5
                break;
            case 6:
                sourceArray  = new boolean[] {true,true,true,true,true,true}; // 6
                break;
            case 7:
                sourceArray  = new boolean[] {false,false,true,true,true,true}; // 7
                break;
            case 8:
                sourceArray  = new boolean[] {true,false,true,true,true,false}; // 8
                break;
            case 9:
                sourceArray  = new boolean[] {false,false,true,true,false,false}; // 9
                break;
            case 10:
                sourceArray  = new boolean[] {false,false,true,true,true,false}; // 10
                break;
            case 11:
                sourceArray  = new boolean[] {true,true,false,true,true,false}; // 11
                break;
            case 12:
                sourceArray  = new boolean[] {true,true,true,true,true,false}; // 12
                break;
            case 13:
                sourceArray  = new boolean[] {false,false,true,false,false,false}; // 13
                break;
            default:
                sourceArray = null;
                break;
        }
        return sourceArray;
    }
}
