package com.blackhornetworkshop.flowrush.initialization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.FlowRush;

//Created by TScissors. inputProcessor который работает только с 1 касанием, поглощает все остальые процессоры ввода если добавлен в мультиплексир первым

public class OneTouchProcessor implements InputProcessor {

    final private FlowRush game;

    public Timer timerForExitDisplay;

    public OneTouchProcessor(FlowRush game){
        this.game = game;
        timerForExitDisplay = new Timer();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            switch (game.screenType){
                case 11: //logo bhw
                    Gdx.app.exit();
                    break;
                case 12: //logo fr
                    Gdx.app.exit();
                    break;
                case 21: //mainmenu
                    //close app message
                    timerForExitDisplay.instance().clear();
                    if(timerForExitDisplay.instance().isEmpty()){
                        game.getMainMenuScr().exitButton.setVisible(true);
                        timerForExitDisplay.schedule(new Task(), 3);
                        timerForExitDisplay.instance().start();
                    }
                    break;
                case 22: //mainmenu authors
                    game.getMainMenuScr().resume();
                    break;
                case 23:// mainmenu support us
                    game.getMainMenuScr().resume();
                    game.getMainMenuScr().setAuthorsScreen();
                    break;
                case 24://mainmenu packchoise
                    game.getMainMenuScr().resume();
                    break;
                case 25://mainmenu lvlchoise
                    game.getMainMenuScr().resume();
                    game.getMainMenuScr().setPackChoiseScreen();
                    break;
                case 31://game
                    game.setMainMenuScreen();
                    break;
                case 32://game pause
                    game.getGameScreen().resume();
                    break;
                case 33://game lvlcomplete
                    game.setMainMenuScreen();
                    break;
                case 34://game packcomplete
                    game.setMainMenuScreen();
                    break;
                case 35://game lvlcomplete+pause
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
            //System.out.println("close app message is not visible");
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
        if(pointer==0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer==0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(pointer==0){
            return false;
        }else {
            return true;
        }
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
