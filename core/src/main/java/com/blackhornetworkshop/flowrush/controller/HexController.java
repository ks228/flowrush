package com.blackhornetworkshop.flowrush.controller;

import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.model.HexActor;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;

//Created by TScissors.

public class HexController {
    public static void setHexbackTouchOff(HexActor actor) {
        if (actor.getIndex() < 25) {
            actor.setBackground(FRAssetManager.getSprite("background_hex"));
        } else {
            actor.setBackground(FRAssetManager.getSprite("background_hex_source"));
        }
    }

    public static void setHexBackgroundOn(HexActor actor) {
        if (actor.getIndex() < 25) {
            actor.setBackground(FRAssetManager.getSprite("background_hex_touched"));
        } else {
            actor.setBackground(FRAssetManager.getSprite("background_hex_touched_source"));
        }
    }

    static void setPowerOn(HexActor actor) {
        actor.setPowerOn();
        if (actor.getInclude() == 2) {
            actor.setIcon(FRAssetManager.getSprite("point_icon_on"));
        } else if (actor.getInclude() == 3) {
            actor.setIcon(FRAssetManager.getSprite("dove_icon_on"));
        }
    }

    static void setPowerOff(HexActor actor) {
        actor.setPowerOff();
        if (actor.getInclude() == 2) {
            if (GameScreen.isSpecialIconsAnimationWhite()) {
                actor.setIcon(FRAssetManager.getSprite("point_icon_white"));
            } else {
                actor.setIcon(FRAssetManager.getSprite("point_icon_off"));
            }
        } else if (actor.getInclude() == 3) {
            actor.setIcon(FRAssetManager.getSprite("dove_icon_off"));
        }
    }

    public static void animIcon(HexActor actor) {
        if (GameScreen.isSpecialIconsAnimationWhite()) {
            actor.setIcon(FRAssetManager.getSprite("point_icon_off"));
        } else {
            actor.setIcon(FRAssetManager.getSprite("point_icon_white"));
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
