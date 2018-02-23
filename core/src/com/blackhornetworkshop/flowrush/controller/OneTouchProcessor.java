package com.blackhornetworkshop.flowrush.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.model.FlowRush;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;

//Created by TScissors.

public class OneTouchProcessor implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        String msg = "Back key. ";
        if (keycode == Input.Keys.BACK) {
            switch (ScreenManager.getCurrentScreen()) {
                case MENU_MAIN:
                    msg = msg + "Show exit button";
                    Timer.instance().clear();
                    UIPool.getExitButton().setVisible(true);
                    Timer.schedule(new Task(), 3);
                    break;
                case MENU_AUTHORS:
                    msg = msg + "To main menu from authors screen";
                    ScreenManager.setMenuMainScreen();
                    break;
                case MENU_SUPPORT_US:
                    msg = msg + "To main menu from support us screen";
                    ScreenManager.setMenuMainScreen();
                    break;
                case MENU_PACK_CHOICE:
                    msg = msg + "To main menu from pack choice us screen";
                    ScreenManager.setMenuMainScreen();
                    break;
                case MENU_LVL_CHOICE:
                    msg = msg + "To pack choice us screen from level choice screen";
                    ScreenManager.setMenuMainScreen();
                    ScreenManager.setMenuPackChoiceScreen();
                    break;
                case GAME_MAIN:
                    msg = msg + "To main menu from game screen";
                    ScreenManager.setMenuMainScreen();
                    break;
                case GAME_PAUSE:
                    msg = msg + "To main game screen from game pause screen";
                    ScreenManager.setGameMainScreen();
                    break;
                case GAME_LVL_COMPLETE:
                    msg = msg + "To main menu from game lvl complete screen";
                    ScreenManager.setMenuMainScreen();
                    break;
                case GAME_PACK_COMPLETE:
                    msg = msg + "To main menu from game pack complete screen";
                    ScreenManager.setMenuMainScreen();
                    break;
                case GAME_LVL_COMPLETE_PAUSE:
                    msg = msg + "To game lvl complete screen from game lvl complete pause screen";
                    ScreenManager.setGameLevelCompleteScreen();
                    break;
                case MENU_GOOGLE_PLAY:
                    msg = msg + "Back to main menu from google play screen";
                    ScreenManager.setMenuMainScreen();
                default:
                    msg = msg + "No action.";
                    break;
            }
            FlowRush.logDebug(msg);
        }
        return false;
    }

    private class Task extends Timer.Task {
        @Override
        public void run() {
            UIPool.getExitButton().setVisible(false);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return pointer != 0;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return pointer != 0;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return pointer != 0;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
