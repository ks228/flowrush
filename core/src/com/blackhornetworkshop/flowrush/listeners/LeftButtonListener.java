package com.blackhornetworkshop.flowrush.listeners;

//Created by TScissors. Слушатель левой диалоговой кнопки

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.screens.GameScreen;
import com.blackhornetworkshop.flowrush.ui.UIPool;

public class LeftButtonListener extends ClickListener{
    public boolean isCancelRateButton;


    public LeftButtonListener(){
        GameScreen.getInstance().firstTap = true;
        isCancelRateButton = false;

    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
        if(GameScreen.getInstance().firstTap) {
            UIPool.getDialogBackground().setText("SEND US FEEDBACK, PLEASE");
            UIPool.getLeftButton().setText("NO, THANKS");
            UIPool.getRightButton().setText("YES");
            GameScreen.getInstance().firstTap = false;
        }else{
            if(!isCancelRateButton){
                FlowRush.getInstance().prefs.setShowRateDialog(false);
                FlowRush.getInstance().savePrefsFile();
            }
            FlowRush.getInstance().setMainMenuScreen();
        }
    }
}
