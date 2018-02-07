package com.blackhornetworkshop.flowrush.controller.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.blackhornetworkshop.flowrush.controller.RateDialogController;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;

//Created by TScissors.

public class RightButtonListener extends ButtonScaleListener {
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
        super.touchUp(event,x,y,pointer,button);
        GameScreen.hideRateDialog();
        if(RateDialogController.isFirstAnswer()) {
            FlowRush.getAndroidHelper().openPlaymarket();
            FlowRush.getInstance().prefs.setShowRateDialog(false);
            FlowRush.getInstance().savePrefsFile();
        }else{
            FlowRush.getAndroidHelper().sendMail();
        }
    }
}
