package com.blackhornetworkshop.flowrush.controller.listeners;

//Created by TScissors.

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.blackhornetworkshop.flowrush.controller.RateDialogController;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;

public class LeftButtonListener extends ButtonScaleListener{
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
        super.touchUp(event,x,y,pointer,button);
        if(RateDialogController.isFirstAnswer()) {
            UIPool.getDialogBackground().setText("SEND US FEEDBACK, PLEASE");
            UIPool.getLeftButton().setText("NO, THANKS");
            UIPool.getRightButton().setText("OK");
            RateDialogController.setIsFirstAnswer(false);
        }else{
            FlowRush.getInstance().prefs.setShowRateDialog(false);
            FlowRush.getInstance().savePrefsFile();

            GameScreen.hideRateDialog();
        }
    }
}
