package com.blackhornetworkshop.flowrush.controller;

import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.model.HexActor;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;

import static com.blackhornetworkshop.flowrush.model.FRConstants.BACKGROUND_HEX;
import static com.blackhornetworkshop.flowrush.model.FRConstants.BACKGROUND_HEX_TOUCHED;
import static com.blackhornetworkshop.flowrush.model.FRConstants.BACKGROUND_HEX_TOUCHED_WITH_SOURCE;
import static com.blackhornetworkshop.flowrush.model.FRConstants.BACKGROUND_HEX_WITH_SOURCE;
import static com.blackhornetworkshop.flowrush.model.FRConstants.DOVE_OFF_ICON;
import static com.blackhornetworkshop.flowrush.model.FRConstants.DOVE_ON_ICON;
import static com.blackhornetworkshop.flowrush.model.FRConstants.POINT_OFF_ICON;
import static com.blackhornetworkshop.flowrush.model.FRConstants.POINT_ON_ICON;
import static com.blackhornetworkshop.flowrush.model.FRConstants.POINT_WHITE_ICON;

//Created by TScissors.

public class HexController {
    public static void setHexbackTouchOff(HexActor actor) {
        if (actor.getIndex() < 25) {
            actor.setBackground(FRAssetManager.getSprite(BACKGROUND_HEX));
        } else {
            actor.setBackground(FRAssetManager.getSprite(BACKGROUND_HEX_WITH_SOURCE));
        }
    }

    public static void setHexBackgroundOn(HexActor actor) {
        if (actor.getIndex() < 25) {
            actor.setBackground(FRAssetManager.getSprite(BACKGROUND_HEX_TOUCHED));
        } else {
            actor.setBackground(FRAssetManager.getSprite(BACKGROUND_HEX_TOUCHED_WITH_SOURCE));
        }
    }

    static void setPowerOn(HexActor actor) {
        actor.setPowerOn();
        if (actor.getInclude() == 2) {
            actor.setIcon(FRAssetManager.getSprite(POINT_ON_ICON));
        } else if (actor.getInclude() == 3) {
            actor.setIcon(FRAssetManager.getSprite(DOVE_ON_ICON));
        }
    }

    static void setPowerOff(HexActor actor) {
        actor.setPowerOff();
        if (actor.getInclude() == 2) {
            if (GameScreen.isSpecialIconsAnimationWhite()) {
                actor.setIcon(FRAssetManager.getSprite(POINT_WHITE_ICON));
            } else {
                actor.setIcon(FRAssetManager.getSprite(POINT_OFF_ICON));
            }
        } else if (actor.getInclude() == 3) {
            actor.setIcon(FRAssetManager.getSprite(DOVE_OFF_ICON));
        }
    }

    public static void animIcon(HexActor actor) {
        if (GameScreen.isSpecialIconsAnimationWhite()) {
            actor.setIcon(FRAssetManager.getSprite(POINT_OFF_ICON));
        } else {
            actor.setIcon(FRAssetManager.getSprite(POINT_WHITE_ICON));
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
