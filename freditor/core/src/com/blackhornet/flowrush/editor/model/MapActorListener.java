package com.blackhornet.flowrush.editor.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;

import com.blackhornet.flowrush.editor.view.EditorScreen;

//Created by TScissors.

public class MapActorListener extends InputListener {

    private com.blackhornet.flowrush.editor.model.MapActor actor;
    private float angle;
    private RotateToAction rotateToAction;


    MapActorListener(MapActor actor) {
        this.actor = actor;

        rotateToAction = new RotateToAction();
        rotateToAction.setDuration(0.5f);

        angle = actor.getAngle();
    }


    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            clearActor();
        }else if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            clearActor();
            if(EditorScreen.indexForBrush!=0) {
                actor.setIndex(EditorScreen.indexForBrush);
            }

        }else if(Gdx.input.isKeyPressed(Input.Keys.D) && actor.getIndex()!=0 && actor.getIndex() > 24 && actor.getIndex() < 37){
            actor.setInclude(3);
            actor.checkInclude();
        }else if(Gdx.input.isKeyPressed(Input.Keys.R) && actor.getIndex()!=0){
            if(actor.isRotable()){
                actor.setNotRotable();
            }else{ actor.setRotable();}
        }else {
            if(actor.isRotable() && actor.getIndex()!=0) {
                angle = actor.getAngle()-60;
                actor.setAngle(angle);

                rotateToAction.setRotation(actor.getAngle());

                if (actor.getActions().contains(rotateToAction, true)) {
                    rotateToAction.restart();
                } else {
                    rotateToAction.reset();
                    actor.addAction(rotateToAction);
                }
            }
        }

        System.out.println("Index: " + actor.getIndex() +" Position: " + actor.getPosition() + " Angle: " +actor.getAngle() + " Include: " +actor.getInclude()+ " Rotable: "+actor.isRotable());

        return true;
    }

    private void clearActor(){
        actor.setTempSprite();

        rotateToAction.setRotation(actor.getAngle());

        if (actor.getActions().contains(rotateToAction, true)) {
            rotateToAction.restart();
        } else {
            rotateToAction.reset();
            actor.addAction(rotateToAction);
        }
    }
}
