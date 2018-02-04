package com.blackhornetworkshop.flowrush.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornetworkshop.flowrush.FlowRush;

//Created by TScissors. Класс для ScaleToAction кнопок по нажатию

public class ButtonScaleListener extends ClickListener{

    private final Actor actor;

    private ScaleToAction buttonScaleToAction;
    public ButtonScaleListener(Actor actr){
        actor = actr;

        buttonScaleToAction = new ScaleToAction();
        buttonScaleToAction.setDuration(0.05f);

    }
    public Actor getActor(){return actor;}

    @Override
    public void clicked(InputEvent event, float x, float y) {
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(FlowRush.getInstance().prefs.isSoundOn()){
            FlowRush.getInstance().tapSound.play(1f);
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
