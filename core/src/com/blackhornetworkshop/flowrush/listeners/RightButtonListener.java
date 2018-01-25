package com.blackhornetworkshop.flowrush.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornetworkshop.flowrush.screens.GameScreen;

//Created by TScissors. Слушатель правой диалоговой кнопки

public class RightButtonListener extends ClickListener {
    final private GameScreen gameScreen;
    private boolean isRateButton = false;
    final LeftButtonListener leftButtonListener;


    public RightButtonListener(final GameScreen gameScreen, final LeftButtonListener leftButtonListener){
        this.gameScreen = gameScreen;
        this.gameScreen.firstTap = true;
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
        if(gameScreen.firstTap) {
            gameScreen.dialogBack.setText("RATE THE GAME, PLEASE");
            gameScreen.rightButton.setText("YES, SURE");
            gameScreen.leftButton.setText("NO, THANKS");
            isRateButton = true;
            leftButtonListener.isCancelRateButton = true;
            gameScreen.firstTap = false;
        }else{
            if(isRateButton){
                gameScreen.game.androidSide.openPlaymarket();
            }else if(!isRateButton){
                gameScreen.game.androidSide.actionSend();
            }
            gameScreen.game.prefs.setShowRateDialog(false);
            gameScreen.game.savePrefsFile();
            gameScreen.game.getGameScreen().dispose();
            gameScreen.game.setMainMenuScreen();
        }
    }
}
