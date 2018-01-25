package com.blackhornetworkshop.flowrush.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

//Created by TScissors. Класс для ScaleToAction кнопок по нажатию

public class ButtonScaleListener extends ClickListener{

    private final Actor actor;
    private final com.blackhornetworkshop.flowrush.FlowRush game;

    private ScaleToAction buttonScaleToAction;
    public ButtonScaleListener(Actor actr, com.blackhornetworkshop.flowrush.FlowRush gam){
        actor = actr;
        game = gam;

        buttonScaleToAction = new ScaleToAction();
        buttonScaleToAction.setDuration(0.05f);

    }
    public Actor getActor(){return actor;}

    @Override
    public void clicked(InputEvent event, float x, float y) {
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(game.prefs.isSoundIsOn()){
            game.tapSound.play(1f);
        }
        buttonScaleToAction.setScale(0.85f, 0.85f);
        if (actor.getActions().contains(buttonScaleToAction, true)) {
            buttonScaleToAction.restart();
        } else {
            buttonScaleToAction.reset();
            actor.addAction(buttonScaleToAction);
        }
        return true;
    }
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
        buttonScaleToAction.setScale(1f, 1f);
        if (actor.getActions().contains(buttonScaleToAction, true)) {
            buttonScaleToAction.restart();
        } else {
            buttonScaleToAction.reset();
            actor.addAction(buttonScaleToAction);
        }
    }
}
