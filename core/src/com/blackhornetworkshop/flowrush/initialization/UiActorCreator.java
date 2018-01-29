package com.blackhornetworkshop.flowrush.initialization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornetworkshop.flowrush.ConstantBase;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener;
import com.blackhornetworkshop.flowrush.ui.SmallButtonActor;

//Created by TScissors. Главный класс игрового экрана

public class UiActorCreator {

    //NEw METHOD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private static SmallButtonActor createSmallButtonActor(float x, float y, float width, float height, boolean visible, String name, Sprite sprite, boolean isScalable, FlowRush flowRush) {
        final SmallButtonActor smallButtonActor = new SmallButtonActor();
        smallButtonActor.setBounds(x, y, width, height);
        smallButtonActor.setOrigin(width / 2.0f, height / 2.0f);
        smallButtonActor.setVisible(visible);
        smallButtonActor.setName(name);
        smallButtonActor.setSprite(sprite);
        if (isScalable) {
            smallButtonActor.addListener(new ButtonScaleListener(smallButtonActor, flowRush));
        }
        return smallButtonActor;
    }
    //NEw METHOD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static TextButton getTextButton(int type, final com.blackhornetworkshop.flowrush.FlowRush game) { //актеры кнопки с текстом
        TextButton textButton;
        switch (type) {
            case 1: //Кнопка PLAY
                textButton = new TextButton("PLAY", game.skin, "playbutton");
                textButton.setSize(Gdx.graphics.getWidth() * 0.9f, ConstantBase.C_BUTTON_SIZE * 1.3f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - ConstantBase.C_BUTTON_SIZE * 4 - textButton.getHeight()));
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        //game.getMainMenuScr().dispose();
                        game.setGameScreen();
                    }
                });
                break;
            case 2://Levels
                textButton = new TextButton("LEVELS", game.skin, "lightblue");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - ConstantBase.C_BUTTON_SIZE * 4 - ConstantBase.C_BUTTON_SIZE * 1.3f - textButton.getHeight() * 1.1f));
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.getMainMenuScr().setPackChoiseScreen();
                    }
                });
                break;
            case 3: //Кнопка SEND FEEDBACK
                textButton = new TextButton("SEND FEEDBACK", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + ConstantBase.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE)) + (ConstantBase.C_BUTTON_SIZE) / 2) - (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 0.3f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.androidSide.sendMail();
                    }
                });
                break;
            case 4: //Кнопка RATE
                textButton = new TextButton("RATE", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + ConstantBase.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE)) + (ConstantBase.C_BUTTON_SIZE) / 2) - (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 1.4f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.androidSide.openPlaymarket();
                    }
                });
                break;
            case 5: //Кнопка SUPPORT US
                textButton = new TextButton("SUPPORT US", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + ConstantBase.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE)) + (ConstantBase.C_BUTTON_SIZE) / 2) - (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 2.5f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.getMainMenuScr().setSupportUsScreen();
                    }
                });
                break;
            case 6: //Кнопка REMOVE ADS
                textButton = new TextButton("REMOVE ADS", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + ConstantBase.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE)) + (ConstantBase.C_BUTTON_SIZE) / 2) - (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 1.9f);
                textButton.setVisible(false);
                break;
            case 7: //Надпись пустышка // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! WHAT ?
                textButton = new TextButton("", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() * 0.98f - textButton.getHeight()));
                textButton.setVisible(false);
                break;
            case 8: //Фон для кнопок соц сетей
                textButton = new TextButton("", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE * 1.5f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (ConstantBase.C_BUTTON_SIZE/ 2 + Gdx.graphics.getHeight() * 0.02f) + ConstantBase.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE)) + (ConstantBase.C_BUTTON_SIZE) / 2) - (ConstantBase.C_BUTTON_SIZE/ 2 + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2 + ConstantBase.C_BUTTON_SIZE * 0.3f);
                textButton.setVisible(false);
                break;
            case 9: //Кнопка MENU в PackComplete
                textButton = new TextButton("MENU", game.skin, "default");
                textButton.setSize(Gdx.graphics.getWidth() * 0.4f, ConstantBase.C_BUTTON_SIZE * 0.8f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()*0.35f) / 4 * 3 - ConstantBase.C_BUTTON_SIZE * 2.9f);
                textButton.setVisible(false);
                textButton.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(textButton, game) {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        //game.getGameScreen().dispose();
                        game.setMainMenuScreen();
                    }
                });
                break;
            case 10: //Кнопка пустышка для диалога об оценке игры
                textButton = new TextButton("", game.skin);
                textButton.setSize(Gdx.graphics.getWidth() * 0.45f, ConstantBase.C_BUTTON_SIZE * 0.8f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth() * 2) / 3, (ConstantBase.C_BUTTON_SIZE * 1.45f - game.skin.getFont("fontSmall").getLineHeight() - ConstantBase.C_BUTTON_SIZE * 0.8f) / 2);
                textButton.setVisible(false);
                break;
            case 12: //Кнопка Next в PackComplete
                textButton = new TextButton("NEXT", game.skin, "darkblue");
                textButton.setSize(Gdx.graphics.getWidth() * 0.5f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()*0.35f) / 4 * 3 - ConstantBase.C_BUTTON_SIZE* 2);
                textButton.setVisible(false);
                textButton.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(textButton, game) {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        //game.getGameScreen().dispose();
                        game.setGameScreen();
                    }
                });
                break;
            case 13://Кнопка EXIT
                textButton = new TextButton("EXIT", game.skin, "lightblue");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - ConstantBase.C_BUTTON_SIZE * 4 - ConstantBase.C_BUTTON_SIZE * 1.3f - textButton.getHeight() * 2.2f));
                textButton.addListener(new ButtonScaleListener(textButton, game) {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.playServices.disconnectGameHelper(); // ADDED !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        Gdx.app.exit(); // DISPOSE ??????????????????????????????????????????????????????????????????
                    }
                });
                break;
            case 14: //Button sign in
                textButton = new TextButton("SIGN IN", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + ConstantBase.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE)) + (ConstantBase.C_BUTTON_SIZE) / 2) - (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 0.3f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.playServices.signIn();
                        game.getMainMenuScr().resume();
                    }
                });
                break;
            case 15: //Button show snapshots
                textButton = new TextButton("LOAD GAME", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + ConstantBase.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE)) + (ConstantBase.C_BUTTON_SIZE) / 2) - (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 0.3f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.playServices.showSavedSnapshots();
                    }
                });
                break;
            case 16: //Button show achievements
                textButton = new TextButton("ACHIEVEMENTS", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, ConstantBase.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + ConstantBase.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE)) + (ConstantBase.C_BUTTON_SIZE) / 2) - (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 1.4f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.playServices.showAchievements();
                    }
                });
                break;
            default:
                textButton = null;
                break;
        }
        textButton.setOrigin(textButton.getWidth() / 2, textButton.getHeight() / 2);
        if (type != 7 && type != 8) {
            textButton.setTransform(true);
        }
        return textButton;
    }

    public static TextButton getPackTextButton(int pack, final com.blackhornetworkshop.flowrush.FlowRush game) { //актеры отвечающие за переход на нужный пак
        TextButton textButton;

        if(game.levelLoader.getLevelPack(pack-1).available) {
            textButton = new TextButton("", game.skin, "darkblue");
        } else {
            textButton = new TextButton("", game.skin, "alphablackgrey");
        }

        String string;
        switch (pack) {
            case 1:
                string = "QUESTION";
                break;
            case 2:
                string = "IDEA";
                break;
            case 3:
                string = "MISSION";
                break;
            case 4:
                //string = "FLYING";
                string = "FLYING";
                break;
            case 5:
                //string = "SOURCE";
                string = "SOURCE\ncoming soon";
                break;
            default:
                string = "NONAME";
                break;
        }

        float up = (Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE); //верхний отступ
        float down = (ConstantBase.C_BUTTON_SIZE) + Gdx.graphics.getHeight() * 0.02f; //нижний отступ
        float packButtonHeight = (up - down) / 6;

        textButton.setText(string);
        textButton.setSize(Gdx.graphics.getWidth() * 0.8f, packButtonHeight);
        textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, up - ((up - down) - textButton.getHeight() * 5 - textButton.getHeight() * 0.1f * 4) / 2 - textButton.getHeight() * 0.1f * (pack - 1) - textButton.getHeight() * pack);
        textButton.setOrigin(textButton.getWidth() / 2, textButton.getHeight() / 2);
        textButton.setTransform(true);

        return textButton;
    }

    public static SmallButtonActor getSmallButtonActor(int type, final FlowRush game) { //Маленькие кнопки актеры
        switch (type) {
            case 1://Иконка паузы
                SmallButtonActor pauseButton= createSmallButtonActor(0.0f, 0.0f, Gdx.graphics.getWidth() / 8, Gdx.graphics.getWidth() / 8, true, "", game.atlas.createSprite("pause_icon"), true, game);
                pauseButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if (game.screenType != ConstantBase.ScreenType.GAME_LVL_COMPLETE) {
                            game.alphawhiteBack.setVisible(true);
                        }
                        game.getGameScreen().pause();
                    }
                });
                return pauseButton;
            case 2: //Иконка назад
                SmallButtonActor backButton = createSmallButtonActor(0.0f, 0.0f, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, true, "", game.atlas.createSprite("back_icon"), true, game);
                backButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.getGameScreen().resume();
                    }
                });
                return backButton;
            case 3://Иконка рестарта уровня
                SmallButtonActor restartButton = createSmallButtonActor(ConstantBase.C_BUTTON_SIZE + Gdx.graphics.getHeight() * 0.05f, 0.0f, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, true, "", game.atlas.createSprite("restart_icon"), false, game);

                restartButton.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(restartButton, game) {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if(game.screenType== ConstantBase.ScreenType.GAME_LVL_COMPLETE_PAUSE){
                            game.levelLoader.prevLvl();
                            game.screenType = ConstantBase.ScreenType.GAME;
                            game.getGameScreen().changeLvl();
                        }else {
                            game.levelLoader.reloadActorList();
                            game.getGameScreen().changeLvl();
                        }
                    }
                });
                return restartButton;
            case 4://Иконка перехода в главное меню из игры
                SmallButtonActor mainMenuButton = createSmallButtonActor(ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, true, "", game.atlas.createSprite("mmenu_icon"), true, game);
                mainMenuButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        //game.getGameScreen().dispose();
                        game.setMainMenuScreen();
                    }
                });
                return mainMenuButton;
            case 5://Иконка контроля звука
                Sprite soundSprite;
                if (game.prefs.isSoundOn()) {
                    soundSprite = game.atlas.createSprite("soundOn_icon");
                }
                else {
                    soundSprite = game.atlas.createSprite("soundOff_icon");
                }
                final SmallButtonActor soundButton = createSmallButtonActor(0.0f, 0.0f, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, true, "", soundSprite, true, game);

                soundButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if (game.prefs.isSoundOn()) {
                            soundButton.sprite = game.atlas.createSprite("soundOff_icon");
                            game.prefs.setSound(false);
                            game.backgroundMusic.pause();
                            game.savePrefsFile();
                            //System.out.println("sound on");
                        } else if (!game.prefs.isSoundOn()) {
                            soundButton.sprite = game.atlas.createSprite("soundOn_icon");
                            game.prefs.setSound(true);
                            game.backgroundMusic.play();
                            game.savePrefsFile();
                            //System.out.println("sound off");
                        }
                    }
                });
                return soundButton;
            case 6://Иконка закрыть в главном меню
                SmallButtonActor closeButton = createSmallButtonActor((Gdx.graphics.getWidth() - ConstantBase.C_BUTTON_SIZE) / 2.0f, Gdx.graphics.getHeight() * 0.02f, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, false, "", game.atlas.createSprite("close_icon"), true, game);
                closeButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.getMainMenuScr().resume();
                    }
                });
                return closeButton;
            case 7://Иконка информации об авторах
                SmallButtonActor authorsButton = createSmallButtonActor(Gdx.graphics.getWidth() - ConstantBase.C_BUTTON_SIZE - Gdx.graphics.getHeight() * 0.02f, Gdx.graphics.getHeight() * 0.02f, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, true, "", game.atlas.createSprite("authors_icon"), true, game);
                authorsButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.getMainMenuScr().setAuthorsScreen();
                    }
                });
                return authorsButton;
            case 8://Кнопка Facebook
                SmallButtonActor fbButton = createSmallButtonActor((Gdx.graphics.getWidth() - ConstantBase.C_BUTTON_SIZE) / 2.0f - ConstantBase.C_BUTTON_SIZE * 1.25f, ConstantBase.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f + ConstantBase.C_BUTTON_SIZE / 2.0f + (Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE + ConstantBase.C_BUTTON_SIZE / 2.0f - (ConstantBase.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2.0f + ConstantBase.C_BUTTON_SIZE * 0.3f + (ConstantBase.C_BUTTON_SIZE * 1.5f - ConstantBase.C_BUTTON_SIZE) / 2.0f, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, false, "", game.atlas.createSprite("fb_icon"), true, game);
                fbButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.androidSide.openFacebook();
                    }
                });
                return fbButton;
            case 9://Кнопка Twitter
                SmallButtonActor twButton = createSmallButtonActor((Gdx.graphics.getWidth() - ConstantBase.C_BUTTON_SIZE) / 2.0f, ConstantBase.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f + ConstantBase.C_BUTTON_SIZE / 2.0f + (Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE + ConstantBase.C_BUTTON_SIZE / 2.0f - (ConstantBase.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2.0f + ConstantBase.C_BUTTON_SIZE * 0.3f + (ConstantBase.C_BUTTON_SIZE * 1.5f - ConstantBase.C_BUTTON_SIZE) / 2.0f, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, false, "", game.atlas.createSprite("tw_icon"), true, game);
                twButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.androidSide.openTwitter();
                    }
                });
                return twButton;
            case 10://Кнопка Vk
                SmallButtonActor vkButton = createSmallButtonActor((Gdx.graphics.getWidth() - ConstantBase.C_BUTTON_SIZE) / 2.0f + ConstantBase.C_BUTTON_SIZE * 1.25f, ConstantBase.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f + ConstantBase.C_BUTTON_SIZE / 2.0f + (Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE + ConstantBase.C_BUTTON_SIZE / 2.0f - (ConstantBase.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f) - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2.0f + ConstantBase.C_BUTTON_SIZE * 0.3f + (ConstantBase.C_BUTTON_SIZE * 1.5f - ConstantBase.C_BUTTON_SIZE) / 2.0f, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, false, "", game.atlas.createSprite("vk_icon"), true, game);
                vkButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.androidSide.openVK();
                    }
                });
                return vkButton;
            case 11://Актер гекс в игре на надписи wellDone
                float welldoneWidth = ConstantBase.C_BUTTON_SIZE * 0.9f;
                float welldoneHeight = ConstantBase.C_BUTTON_SIZE * 0.9f * 0.8947368f;
                SmallButtonActor welldoneHex = createSmallButtonActor((Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * 0.6f) / 2.0f - welldoneWidth / 2.0f, Gdx.graphics.getHeight() - welldoneHeight - (ConstantBase.C_BUTTON_SIZE - welldoneHeight) / 2.0f, welldoneWidth, welldoneHeight, false, "", game.atlas.createSprite("bighex_light"), false, game);

                RotateByAction rotateToActionWellDone = new RotateByAction();
                rotateToActionWellDone.setDuration(2f);
                rotateToActionWellDone.setAmount(-360);

                RepeatAction repeatActionWellDone = new RepeatAction();
                repeatActionWellDone.setAction(rotateToActionWellDone);
                repeatActionWellDone.setCount(RepeatAction.FOREVER);

                welldoneHex.addAction(repeatActionWellDone);
                return welldoneHex;
            case 12://Кнопка NEXT lvlcomplete
                final SmallButtonActor nextButton = createSmallButtonActor(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 8, 0.0f, Gdx.graphics.getWidth() / 8, Gdx.graphics.getWidth() / 8, false, "", game.atlas.createSprite("next_icon"), true, game);
                nextButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.screenType = ConstantBase.ScreenType.GAME;
                        if(nextButton.getName().equals("show pack")){
                            game.getGameScreen().showPackComplete();
                            nextButton.setName("");
                        }else {
                            game.getGameScreen().changeLvl();
                        }
                    }
                });
                MoveToAction moveToAction1 = new MoveToAction();
                moveToAction1.setDuration(0.5f);
                moveToAction1.setPosition(Gdx.graphics.getWidth()-nextButton.getWidth()*1.3f,0);

                MoveToAction moveToAction2 = new MoveToAction();
                moveToAction2.setDuration(0.5f);
                moveToAction2.setPosition(Gdx.graphics.getWidth()-nextButton.getWidth(),0);

                SequenceAction seqActionNextButton = new SequenceAction(moveToAction1, moveToAction2);

                RepeatAction repeatActionNext = new RepeatAction();
                repeatActionNext.setAction(seqActionNextButton);
                repeatActionNext.setCount(RepeatAction.FOREVER);

                nextButton.addAction(repeatActionNext);
                return nextButton;
            case 13://Кнопка SupportUs mainmenuscreen
                SmallButtonActor supportUsButton = createSmallButtonActor((Gdx.graphics.getWidth() - ConstantBase.C_BUTTON_SIZE) / 2.0f, Gdx.graphics.getHeight() * 0.02f, ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE, true, "", game.atlas.createSprite("ads_icon"), true, game);
                supportUsButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.getMainMenuScr().setSupportUsScreen();
                    }
                });
                return supportUsButton;
            case 14://Google Play Games button
                Sprite googlePlaySprite = new Sprite(new Texture(Gdx.files.internal("controller.png")));
                SmallButtonActor googlePlayButton = createSmallButtonActor(Gdx.graphics.getHeight() * 0.02f, Gdx.graphics.getHeight() - ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE*0.8f, ConstantBase.C_BUTTON_SIZE*0.8f, true, "", googlePlaySprite, true, game);
                googlePlayButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if(game.playServices.isSignedIn()){
                            game.getMainMenuScr().setSignedScreen();
                        }else {
                            game.getMainMenuScr().setSignInScreen();
                        }
                    }
                });
                return googlePlayButton ;
            default:
                return null;
        }
    }
}