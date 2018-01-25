package com.blackhornetworkshop.flowrush.listeners;

//Created by TScissors. Слушатель левой диалоговой кнопки

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornetworkshop.flowrush.screens.GameScreen;

public class LeftButtonListener extends ClickListener{
    final private GameScreen gameScreen;
    public boolean isCancelRateButton;


    public LeftButtonListener(final GameScreen gameScreen){
        this.gameScreen = gameScreen;
        this.gameScreen.firstTap = true;
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
        if(gameScreen.firstTap) {
            gameScreen.dialogBack.setText("SEND US FEEDBACK, PLEASE");
            gameScreen.leftButton.setText("NO, THANKS");
            gameScreen.rightButton.setText("YES");
            gameScreen.firstTap = false;
        }else{
            if(!isCancelRateButton){
                gameScreen.game.prefs.setShowRateDialog(false);
                gameScreen.game.savePrefsFile();
            }
            gameScreen.game.getGameScreen().dispose();
            gameScreen.game.setMainMenuScreen();
        }
    }
}
