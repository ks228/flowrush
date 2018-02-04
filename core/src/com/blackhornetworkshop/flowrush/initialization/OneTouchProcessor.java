package com.blackhornetworkshop.flowrush.initialization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.UIPool;
import com.blackhornetworkshop.flowrush.screens.GameScreen;
import com.blackhornetworkshop.flowrush.screens.MenuScreen;

//Created by TScissors. Disables multitouch, intercepts back key

public class OneTouchProcessor implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        String msg = "Back key. ";
        if (keycode == Input.Keys.BACK) {
            switch (FlowRush.getInstance().screenType) {
                case LOGO_BHW:
                    msg = msg + "App exit";
                    Gdx.app.exit();
                    break;
                case LOGO_FR:
                    msg = msg + "App exit";
                    Gdx.app.exit();
                    break;
                case MAIN_MENU:
                    //close app message
                    msg = msg + "Show exit button";
                    Timer.instance().clear();
                    if (Timer.instance().isEmpty()) {
                        UIPool.getExitButton().setVisible(true);
                        Timer.schedule(new Task(), 3);
                        Timer.instance().start();
                    }
                    break;
                case MAIN_MENU_AUTHORS:
                    msg = msg + "To main menu from authors screen";
                    MenuScreen.getInstance().resume();
                    break;
                case MAIN_MENU_SUPPORT_US:
                    msg = msg + "To main menu from support us screen";
                    MenuScreen.getInstance().resume();
                    break;
                case MAIN_MENU_PACK_CHOICE:
                    msg = msg + "To main menu from pack choice us screen";
                    MenuScreen.getInstance().resume();
                    break;
                case MAIN_MENU_LVL_CHOICE:
                    msg = msg + "To pack choice us screen from level choice screen";
                    MenuScreen.getInstance().resume();
                    MenuScreen.getInstance().setPackChoiseScreen();
                    break;
                case GAME:
                    msg = msg + "To main menu from game screen";
                    FlowRush.getInstance().setMainMenuScreen();
                    break;
                case GAME_PAUSE:
                    msg = msg + "To main menu from game pause screen";
                    GameScreen.getInstance().resume();
                    break;
                case GAME_LVL_COMPLETE:
                    msg = msg + "To main menu from game lvl complete screen";
                    FlowRush.getInstance().setMainMenuScreen();
                    break;
                case GAME_PACK_COMPLETE:
                    msg = msg + "To main menu from game pack complete screen";
                    FlowRush.getInstance().setMainMenuScreen();
                    break;
                case GAME_LVL_COMPLETE_PAUSE:
                    msg = msg + "To main menu from game lvl complete pause screen";
                    GameScreen.getInstance().resume();
                    break;
                case MAIN_MENU_GOOGLE_PLAY:
                    msg = msg + "Back to main menu from google play screen";
                    MenuScreen.getInstance().resume();
                default:
                    msg = msg + "No action.";
                    break;
            }
        }
        FlowRush.logDebug(msg);
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
