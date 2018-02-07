package com.blackhornetworkshop.flowrush.controller;

import com.blackhornetworkshop.flowrush.model.HexActor;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;

//Created by TScissors.

public class HexController {

    public static void moveSources(HexActor actor) {
        boolean[] sourceArray = actor.getSourceArray();
        boolean val = sourceArray[0];
        sourceArray[0] = sourceArray[sourceArray.length - 1];
        for (int j = 0; j < sourceArray.length - 1; ++j) {
            boolean val2 = sourceArray[j + 1];
            sourceArray[j + 1] = val;
            val = val2;
        }
        actor.setSources(sourceArray);
    }

    public static void setHexbackTouchOff(HexActor actor) {
        if (actor.getIndex() < 25) {
            actor.setBackground(FRAssetManager.getHexBackgroundOff());
        } else {
            actor.setBackground(FRAssetManager.getHexBackgroundOffWithSource());
        }
    }

    public static void setHexBackgroundOn(HexActor actor) {
        if (actor.getIndex() < 25) {
            actor.setBackground(FRAssetManager.getHexBackgroundOn());
        } else {
            actor.setBackground(FRAssetManager.getHexBackgroundOnWithSource());
        }
    }

    static void setPowerOn(HexActor actor) {
        actor.setPowerOn();
        if (actor.getInclude() == 2) {
            actor.setIcon(FRAssetManager.getIconPointOn());
        } else if (actor.getInclude() == 3) {
            actor.setIcon(FRAssetManager.getIconDoveOn());
        }
    }

    static void setPowerOff(HexActor actor) {
        actor.setPowerOff();
        if (actor.getInclude() == 2) {
            if (GameScreen.isSpecialIconsAnimationWhite()) {
                actor.setIcon(FRAssetManager.getIconPointWhite());
            } else {
                actor.setIcon(FRAssetManager.getIconPointOff());
            }
        } else if (actor.getInclude() == 3) {
            actor.setIcon(FRAssetManager.getIconDoveOff());
        }
    }

    public static void animIcon(HexActor actor) {
        if (GameScreen.isSpecialIconsAnimationWhite()) {
            actor.setIcon(FRAssetManager.getIconPointOff());
        } else {
            actor.setIcon(FRAssetManager.getIconPointWhite());
        }
    }

    public static void setAngle(HexActor actor, float angle) {
        actor.setAngle(angle);
        actor.setRotatePosition(actor.getRotatePosition() + 1);
        if (actor.getRotatePosition() == 6) {
            actor.setRotatePosition(0);
        }
    }
}
