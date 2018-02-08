package com.blackhornetworkshop.flowrush.controller.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornetworkshop.flowrush.controller.FRAssetManager;
import com.blackhornetworkshop.flowrush.view.FlowRush;

//Created by TScissors.

public class ButtonScaleListener extends ClickListener {
    private ScaleToAction buttonScale1;
    private ScaleToAction buttonScale2;

    public ButtonScaleListener(boolean disableScaleAnimationBack) {
        buttonScale1 = new ScaleToAction();
        buttonScale1.setScale(0.85f, 0.85f);
        buttonScale1.setDuration(0.05f);
        buttonScale2 = new ScaleToAction();
        buttonScale2.setScale(1f, 1f);
        if(disableScaleAnimationBack){
            buttonScale2.setDuration(0);
        }else{
            buttonScale2.setDuration(0.05f);
        }
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (FlowRush.getPreferences().isSoundOn()) {
            FRAssetManager.getTapSound().play();
        }
        if (event.getListenerActor().getActions().contains(buttonScale1, true)) {
            buttonScale1.restart();
        } else {
            buttonScale1.reset();
            event.getListenerActor().addAction(buttonScale1);
        }
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        buttonScale1.finish();
        if (event.getListenerActor().getActions().contains(buttonScale2, true)) {
            buttonScale2.restart();
        } else {
            buttonScale2.reset();
            event.getListenerActor().addAction(buttonScale2);
        }
        action(event);
    }

    public void action(InputEvent event) {
    }
}
