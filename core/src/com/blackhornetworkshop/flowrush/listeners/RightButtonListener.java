package com.blackhornetworkshop.flowrush.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.screens.GameScreen;
import com.blackhornetworkshop.flowrush.ui.UIPool;

//Created by TScissors. Слушатель правой диалоговой кнопки

public class RightButtonListener extends ClickListener {
    private boolean isRateButton = false;
    private final LeftButtonListener leftButtonListener;

    public RightButtonListener(final LeftButtonListener leftButtonListener){
        GameScreen.getInstance().firstTap = true;
        this.leftButtonListener = leftButtonListener;
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
            UIPool.getDialogBackground().setText("RATE THE GAME, PLEASE");
            UIPool.getRightButton().setText("YES, SURE");
            UIPool.getLeftButton().setText("NO, THANKS");
            isRateButton = true;
            leftButtonListener.isCancelRateButton = true;
            GameScreen.getInstance().firstTap = false;
        }else{
            if(isRateButton){
                FlowRush.getAndroidHelper().openPlaymarket();
            }else{
                FlowRush.getAndroidHelper().sendMail();
            }
            FlowRush.getInstance().prefs.setShowRateDialog(false);
            FlowRush.getInstance().savePrefsFile();
            FlowRush.getInstance().setMainMenuScreen();
        }
    }
}
