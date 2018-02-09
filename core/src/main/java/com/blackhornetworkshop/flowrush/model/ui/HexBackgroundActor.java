package com.blackhornetworkshop.flowrush.model.ui;

//Created by TScissors.

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.model.FRConstants;


public class HexBackgroundActor extends Actor {

    private Sprite tapEffect;
    private ScaleToAction scale1;
    private ScaleToAction scale2;
    private SequenceAction sequenceAction;

    HexBackgroundActor(Sprite sprite){
        setPosition(-1000, -1000);
        setSize(FRConstants.HEX_WIDTH, FRConstants.HEX_HEIGHT);
        setOrigin(Align.center);

        tapEffect = sprite;

        scale1 = new ScaleToAction();
        scale1.setScale(1f, 1f);
        scale1.setDuration(0.2f);

        scale2 = new ScaleToAction();
        scale2.setScale(0f, 0f);
        scale2.setDuration(0.0f);

        sequenceAction = new SequenceAction(scale1, scale2);
    }

    public void goAnim(float xPos, float yPos){
        setX(xPos);
        setY(yPos);

        if (getActions().contains(sequenceAction, true)) {
            setScale(0);
            sequenceAction.restart();
            scale1.restart();
            scale2.restart();
            sequenceAction.addAction(scale1);
            sequenceAction.addAction(scale2);
        } else {
            sequenceAction.restart();
            scale1.reset();
            scale2.reset();
            addAction(sequenceAction);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(tapEffect, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
