package com.blackhornetworkshop.flowrush.model.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornetworkshop.flowrush.controller.SourceChecker;
import com.blackhornetworkshop.flowrush.model.HexActor;
import com.blackhornetworkshop.flowrush.controller.HexController;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;

//Created by TScissors.

public class HexActorListener extends ClickListener {

    private HexActor actor;
    private float angle;
    private RotateToAction rotateToAction;
    private ScaleToAction scaleToAction;

    private RotateToAction rotateToAction1, rotateToAction2;
    private SequenceAction sequenceAction;

    public HexActorListener(HexActor actor) {
        this.actor = actor;

        scaleToAction = new ScaleToAction();
        scaleToAction.setDuration(0.05f);

        rotateToAction = new RotateToAction();
        rotateToAction.setDuration(0.13f);
        angle = actor.getAngle();

        rotateToAction1 = new RotateToAction();
        rotateToAction1.setDuration(0.05f);

        rotateToAction2 = new RotateToAction();
        rotateToAction2.setDuration(0.05f);

        rotateToAction1.setRotation(actor.getAngle() - 10);
        rotateToAction2.setRotation(actor.getAngle());

        sequenceAction = new SequenceAction(rotateToAction1, rotateToAction2);
    }


    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        if(Gdx.input.isTouched()&&actor.isRotable()){

            scaleToAction.setScale(0.85f, 0.85f);

            if (actor.getActions().contains(scaleToAction, true)) {
                scaleToAction.restart();
            } else {
                scaleToAction.reset();
                actor.addAction(scaleToAction);
            }

        }
        HexController.setHexBackgroundOn(actor);
        return true;
    }
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
        HexController.setHexbackTouchOff(actor);

        if (actor.isRotable()) {
            scaleToAction.setScale(1f, 1f);

            if (actor.getActions().contains(scaleToAction, true)) {
                scaleToAction.restart();
            } else {
                scaleToAction.reset();
                actor.addAction(scaleToAction);
            }

            angle = actor.getAngle() - 60;

            HexController.setAngle(actor, angle);
            actor.moveSources();

            SourceChecker.getInstance().update();

            rotateToAction.setRotation(actor.getAngle());
            if (actor.getActions().contains(rotateToAction, true)) {
                rotateToAction.restart();
            } else {
                rotateToAction.reset();
                actor.addAction(rotateToAction);

            }

        }else{
            sequenceAction.restart();
            rotateToAction1.reset();
            rotateToAction2.reset();
            actor.addAction(sequenceAction);
        }

        UIPool.getHexBackgroundActor().goAnim(actor.getX(),actor.getY());
    }

}
