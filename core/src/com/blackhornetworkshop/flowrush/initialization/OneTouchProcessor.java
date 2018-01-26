package com.blackhornetworkshop.flowrush.initialization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.FlowRush;

//Created by TScissors. inputProcessor который работает только с 1 касанием, поглощает все остальые процессоры ввода если добавлен в мультиплексир первым

public class OneTouchProcessor implements InputProcessor {

    final private FlowRush game;

    public OneTouchProcessor(FlowRush game){
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            switch (game.screenType){
                case LOGO_BHW: //logo bhw
                    Gdx.app.exit();
                    break;
                case LOGO_FR: //logo fr
                    Gdx.app.exit();
                    break;
                case MAIN_MENU: //mainmenu
                    //close app message
                    Timer.instance().clear();
                    if(Timer.instance().isEmpty()){
                        game.getMainMenuScr().exitButton.setVisible(true);
                        Timer.schedule(new Task(), 3);
                        Timer.instance().start();
                    }
                    break;
                case MAIN_MENU_AUTHORS: //mainmenu authors
                    game.getMainMenuScr().resume();
                    break;
                case MAIN_MENU_SUPPORT_US:// mainmenu support us
                    game.getMainMenuScr().resume();
                    game.getMainMenuScr().setAuthorsScreen();
                    break;
                case MAIN_MENU_PACK_CHOISE://mainmenu packchoise
                    game.getMainMenuScr().resume();
                    break;
                case MAINE_MENU_LVL_CHOISE://mainmenu lvlchoise
                    game.getMainMenuScr().resume();
                    game.getMainMenuScr().setPackChoiseScreen();
                    break;
                case GAME://game
                    game.setMainMenuScreen();
                    break;
                case GAME_PAUSE://game pause
                    game.getGameScreen().resume();
                    break;
                case GAME_LVL_COMPLETE://game lvlcomplete
                    game.setMainMenuScreen();
                    break;
                case GAME_PACK_COMPLETE://game packcomplete
                    game.setMainMenuScreen();
                    break;
                case GAME_LVL_COMPLETE_PAUSE://game lvlcomplete+pause
                    game.getGameScreen().resume();
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    private class Task extends Timer.Task{
        @Override
        public void run() {
            System.out.println("close app message is not visible");
            game.getMainMenuScr().exitButton.setVisible(false);
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
