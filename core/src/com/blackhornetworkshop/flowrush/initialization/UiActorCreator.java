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
import com.blackhornetworkshop.flowrush.FRAssetManager;
import com.blackhornetworkshop.flowrush.FRConstants;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener;
import com.blackhornetworkshop.flowrush.screens.GameScreen;
import com.blackhornetworkshop.flowrush.screens.MenuScreen;
import com.blackhornetworkshop.flowrush.ui.SmallButtonActor;
import com.blackhornetworkshop.flowrush.ui.UIPool;
import com.blackhornetworkshop.flowrush.ui.background.BackgroundActor;
import com.blackhornetworkshop.flowrush.ui.background.BottomBackgroundActor;
import com.blackhornetworkshop.flowrush.ui.background.LeftBackgroundActor;
import com.blackhornetworkshop.flowrush.ui.background.RightBackgroundActor;
import com.blackhornetworkshop.flowrush.ui.background.TopBackgroundActor;

//Created by TScissors. Главный класс игрового экрана

public class UiActorCreator {

    //NEw METHOD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private static SmallButtonActor createSmallButtonActor(float x, float y, float width, float height, boolean visible, String name, Sprite sprite, boolean isScalable) {
        final SmallButtonActor smallButtonActor = new SmallButtonActor();
        smallButtonActor.setBounds(x, y, width, height);
        smallButtonActor.setOrigin(width / 2.0f, height / 2.0f);
        smallButtonActor.setVisible(visible);
        smallButtonActor.setName(name);
        smallButtonActor.setSprite(sprite);
        if (isScalable) {
            smallButtonActor.addListener(new ButtonScaleListener());
        }
        return smallButtonActor;
    }
    //NEw METHOD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static TextButton getTextButton(int type) { //актеры кнопки с текстом
        TextButton textButton;
        switch (type) {
            case 1: //Кнопка PLAY
                textButton = new TextButton("PLAY", FRAssetManager.getSkin(), "playbutton");
                textButton.setSize(Gdx.graphics.getWidth() * 0.9f, FRConstants.C_BUTTON_SIZE * 1.3f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - FRConstants.C_BUTTON_SIZE * 4 - textButton.getHeight()));
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        //game.getMenuScreen().dispose();
                        FlowRush.getInstance().setGameScreen();
                    }
                });
                break;
            case 2://Levels
                textButton = new TextButton("LEVELS", FRAssetManager.getSkin(), "lightblue");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - FRConstants.C_BUTTON_SIZE * 4 - FRConstants.C_BUTTON_SIZE * 1.3f - textButton.getHeight() * 1.1f));
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        MenuScreen.getInstance().setPackChoiseScreen();
                    }
                });
                break;
            case 3: //Кнопка SEND FEEDBACK
                textButton = new TextButton("SEND FEEDBACK", FRAssetManager.getSkin(), "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + FRConstants.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE)) + (FRConstants.C_BUTTON_SIZE) / 2) - (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 0.3f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        FlowRush.getAndroidHelper().sendMail();
                    }
                });
                break;
            case 4: //Кнопка RATE
                textButton = new TextButton("RATE", FRAssetManager.getSkin(), "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + FRConstants.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE)) + (FRConstants.C_BUTTON_SIZE) / 2) - (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 1.4f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        FlowRush.getAndroidHelper().openPlaymarket();
                    }
                });
                break;
            case 5: //Кнопка SUPPORT US
                textButton = new TextButton("SUPPORT US", FRAssetManager.getSkin(), "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + FRConstants.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE)) + (FRConstants.C_BUTTON_SIZE) / 2) - (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 2.5f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        MenuScreen.getInstance().setSupportUsScreen();
                    }
                });
                break;
            case 6: //Кнопка REMOVE ADS
                textButton = new TextButton("REMOVE ADS", FRAssetManager.getSkin(), "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + FRConstants.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE)) + (FRConstants.C_BUTTON_SIZE) / 2) - (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 1.9f);
                textButton.setVisible(false);
                break;
            case 7: //Надпись пустышка // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! WHAT ?
                textButton = new TextButton("", FRAssetManager.getSkin(), "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() * 0.98f - textButton.getHeight()));
                textButton.setVisible(false);
                break;
            case 8: //Фон для кнопок соц сетей
                textButton = new TextButton("", FRAssetManager.getSkin(), "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE * 1.5f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (FRConstants.C_BUTTON_SIZE/ 2 + Gdx.graphics.getHeight() * 0.02f) + FRConstants.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE)) + (FRConstants.C_BUTTON_SIZE) / 2) - (FRConstants.C_BUTTON_SIZE/ 2 + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2 + FRConstants.C_BUTTON_SIZE * 0.3f);
                textButton.setVisible(false);
                break;
            case 9: //Кнопка MENU в PackComplete
                textButton = new TextButton("MENU", FRAssetManager.getSkin(), "default");
                textButton.setSize(Gdx.graphics.getWidth() * 0.4f, FRConstants.C_BUTTON_SIZE * 0.8f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()*0.35f) / 4 * 3 - FRConstants.C_BUTTON_SIZE * 2.9f);
                textButton.setVisible(false);
                textButton.addListener(new ButtonScaleListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        //game.getGameScreen().dispose();
                        FlowRush.getInstance().setMainMenuScreen();
                    }
                });
                break;
            case 10: //Кнопка пустышка для диалога об оценке игры
                textButton = new TextButton("", FRAssetManager.getSkin());
                textButton.setSize(Gdx.graphics.getWidth() * 0.45f, FRConstants.C_BUTTON_SIZE * 0.8f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth() * 2) / 3, (FRConstants.C_BUTTON_SIZE * 1.45f - FRAssetManager.getSkin().getFont("fontSmall").getLineHeight() - FRConstants.C_BUTTON_SIZE * 0.8f) / 2);
                textButton.setVisible(false);
                break;
            case 12: //Кнопка Next в PackComplete
                textButton = new TextButton("NEXT", FRAssetManager.getSkin(), "darkblue");
                textButton.setSize(Gdx.graphics.getWidth() * 0.5f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()*0.35f) / 4 * 3 - FRConstants.C_BUTTON_SIZE* 2);
                textButton.setVisible(false);
                textButton.addListener(new ButtonScaleListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        FlowRush.getInstance().setGameScreen();
                    }
                });
                break;
            case 13://Кнопка EXIT
                textButton = new TextButton("EXIT", FRAssetManager.getSkin(), "lightblue");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - FRConstants.C_BUTTON_SIZE * 4 - FRConstants.C_BUTTON_SIZE * 1.3f - textButton.getHeight() * 2.2f));
                textButton.addListener(new ButtonScaleListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.exit(); // DISPOSE ??????????????????????????????????????????????????????????????????
                    }
                });
                break;
            case 14: //Button sign in
                textButton = new TextButton("SIGN IN", FRAssetManager.getSkin(), "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + FRConstants.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE)) + (FRConstants.C_BUTTON_SIZE) / 2) - (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 0.3f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        FlowRush.logDebug("Sign In button was pressed");
                        FlowRush.getPlayServices().signIn();
                        MenuScreen.getInstance().resume();
                    }
                });
                break;
            case 15: //Button show snapshots
                textButton = new TextButton("LOAD GAME", FRAssetManager.getSkin(), "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + FRConstants.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE)) + (FRConstants.C_BUTTON_SIZE) / 2) - (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 2.5f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        FlowRush.logDebug("Load button was pressed");
                        FlowRush.getPlayServices().showSavedSnapshots();
                    }
                });
                break;
            case 16: //Button show achievements
                textButton = new TextButton("ACHIEVEMENTS", FRAssetManager.getSkin(), "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + FRConstants.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE)) + (FRConstants.C_BUTTON_SIZE) / 2) - (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 1.4f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        FlowRush.logDebug("Achievements button was pressed");
                        FlowRush.getPlayServices().showAchievements();
                    }
                });
                break;
            case 17: //Button sign out
                textButton = new TextButton("SIGN OUT", FRAssetManager.getSkin(), "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, FRConstants.C_BUTTON_SIZE);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) + FRConstants.C_BUTTON_SIZE / 2 + ((((Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE)) + (FRConstants.C_BUTTON_SIZE) / 2) - (FRConstants.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2 + textButton.getHeight() * 0.3f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        FlowRush.logDebug("Sign Out button was pressed");
                        FlowRush.getPlayServices().signOut();
                        MenuScreen.getInstance().resume();
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

    public static TextButton getPackTextButton(int pack) { //актеры отвечающие за переход на нужный пак
        TextButton textButton;

        if(LevelLoader.getInstance().getLevelPack(pack-1).available) {
            textButton = new TextButton("", FRAssetManager.getSkin(), "darkblue");
        } else {
            textButton = new TextButton("", FRAssetManager.getSkin(), "alphablackgrey");
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

        float up = (Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE); //верхний отступ
        float down = (FRConstants.C_BUTTON_SIZE) + Gdx.graphics.getHeight() * 0.02f; //нижний отступ
        float packButtonHeight = (up - down) / 6;

        textButton.setText(string);
        textButton.setSize(Gdx.graphics.getWidth() * 0.8f, packButtonHeight);
        textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, up - ((up - down) - textButton.getHeight() * 5 - textButton.getHeight() * 0.1f * 4) / 2 - textButton.getHeight() * 0.1f * (pack - 1) - textButton.getHeight() * pack);
        textButton.setOrigin(textButton.getWidth() / 2, textButton.getHeight() / 2);
        textButton.setTransform(true);

        return textButton;
    }

    public static SmallButtonActor getSmallButtonActor(int type) { //Маленькие кнопки актеры
        switch (type) {
            case 1://Иконка паузы
                SmallButtonActor pauseButton= createSmallButtonActor(0.0f, 0.0f, Gdx.graphics.getWidth() / 8, Gdx.graphics.getWidth() / 8, true, "", FRAssetManager.getAtlas().createSprite("pause_icon"), true);
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
                        if (FlowRush.getInstance().screenType != FRConstants.ScreenType.GAME_LVL_COMPLETE) {
                            UIPool.getPauseBackground().setVisible(true);
                        }
                        GameScreen.getInstance().pause();
                    }
                });
                return pauseButton;
            case 2: //Иконка назад
                SmallButtonActor backButton = createSmallButtonActor(0.0f, 0.0f, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, true, "", FRAssetManager.getAtlas().createSprite("back_icon"), true);
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
                        GameScreen.getInstance().resume();
                    }
                });
                return backButton;
            case 3://Иконка рестарта уровня
                SmallButtonActor restartButton = createSmallButtonActor(FRConstants.C_BUTTON_SIZE + Gdx.graphics.getHeight() * 0.05f, 0.0f, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, true, "", FRAssetManager.getAtlas().createSprite("restart_icon"), false);

                restartButton.addListener(new ButtonScaleListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if(FlowRush.getInstance().screenType== FRConstants.ScreenType.GAME_LVL_COMPLETE_PAUSE){
                            LevelLoader.getInstance().prevLvl();
                            FlowRush.getInstance().screenType = FRConstants.ScreenType.GAME;
                            GameScreen.getInstance().startNewLevel();
                        }else {
                            LevelLoader.getInstance().reloadActorList();
                            GameScreen.getInstance().startNewLevel();
                        }
                    }
                });
                return restartButton;
            case 4://Иконка перехода в главное меню из игры
                SmallButtonActor mainMenuButton = createSmallButtonActor(FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, true, "", FRAssetManager.getAtlas().createSprite("mmenu_icon"), true);
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
                        FlowRush.getInstance().setMainMenuScreen();
                    }
                });
                return mainMenuButton;
            case 5://Иконка контроля звука
                Sprite soundSprite;
                if (FlowRush.getInstance().prefs.isSoundOn()) {
                    soundSprite = FRAssetManager.getAtlas().createSprite("soundOn_icon");
                }
                else {
                    soundSprite = FRAssetManager.getAtlas().createSprite("soundOff_icon");
                }
                final SmallButtonActor soundButton = createSmallButtonActor(0.0f, 0.0f, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, true, "", soundSprite, true);

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
                        if (FlowRush.getInstance().prefs.isSoundOn()) {
                            soundButton.sprite = FRAssetManager.getAtlas().createSprite("soundOff_icon");
                            FlowRush.getInstance().prefs.setSound(false);
                            FRAssetManager.getBackgroundMusic().pause();
                            FlowRush.getInstance().savePrefsFile();
                            //System.out.println("sound on");
                        } else if (!FlowRush.getInstance().prefs.isSoundOn()) {
                            soundButton.sprite = FRAssetManager.getAtlas().createSprite("soundOn_icon");
                            FlowRush.getInstance().prefs.setSound(true);
                            FRAssetManager.getBackgroundMusic().play();
                            FlowRush.getInstance().savePrefsFile();
                            //System.out.println("sound off");
                        }
                    }
                });
                return soundButton;
            case 6://Иконка закрыть в главном меню
                SmallButtonActor closeButton = createSmallButtonActor((Gdx.graphics.getWidth() - FRConstants.C_BUTTON_SIZE) / 2.0f, Gdx.graphics.getHeight() * 0.02f, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, false, "", FRAssetManager.getAtlas().createSprite("close_icon"), true);
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
                        MenuScreen.getInstance().resume();
                    }
                });
                return closeButton;
            case 7://Иконка информации об авторах
                SmallButtonActor authorsButton = createSmallButtonActor(Gdx.graphics.getWidth() - FRConstants.C_BUTTON_SIZE - Gdx.graphics.getHeight() * 0.02f, Gdx.graphics.getHeight() * 0.02f, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, true, "", FRAssetManager.getAtlas().createSprite("authors_icon"), true);
                authorsButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        MenuScreen.getInstance().setAuthorsScreen();
                    }
                });
                return authorsButton;
            case 8://Кнопка Facebook
                SmallButtonActor fbButton = createSmallButtonActor((Gdx.graphics.getWidth() - FRConstants.C_BUTTON_SIZE) / 2.0f - FRConstants.C_BUTTON_SIZE * 1.25f, FRConstants.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f + FRConstants.C_BUTTON_SIZE / 2.0f + (Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE + FRConstants.C_BUTTON_SIZE / 2.0f - (FRConstants.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2.0f + FRConstants.C_BUTTON_SIZE * 0.3f + (FRConstants.C_BUTTON_SIZE * 1.5f - FRConstants.C_BUTTON_SIZE) / 2.0f, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, false, "", FRAssetManager.getAtlas().createSprite("fb_icon"), true);
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
                        FlowRush.getAndroidHelper().openFacebook();
                    }
                });
                return fbButton;
            case 9://Кнопка Twitter
                SmallButtonActor twButton = createSmallButtonActor((Gdx.graphics.getWidth() - FRConstants.C_BUTTON_SIZE) / 2.0f, FRConstants.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f + FRConstants.C_BUTTON_SIZE / 2.0f + (Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE + FRConstants.C_BUTTON_SIZE / 2.0f - (FRConstants.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2.0f + FRConstants.C_BUTTON_SIZE * 0.3f + (FRConstants.C_BUTTON_SIZE * 1.5f - FRConstants.C_BUTTON_SIZE) / 2.0f, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, false, "", FRAssetManager.getAtlas().createSprite("tw_icon"), true);
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
                        FlowRush.getAndroidHelper().openTwitter();
                    }
                });
                return twButton;
            case 10://Кнопка Vk
                SmallButtonActor vkButton = createSmallButtonActor((Gdx.graphics.getWidth() - FRConstants.C_BUTTON_SIZE) / 2.0f + FRConstants.C_BUTTON_SIZE * 1.25f, FRConstants.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f + FRConstants.C_BUTTON_SIZE / 2.0f + (Gdx.graphics.getHeight() * 0.98f - FRConstants.C_BUTTON_SIZE + FRConstants.C_BUTTON_SIZE / 2.0f - (FRConstants.C_BUTTON_SIZE / 2.0f + Gdx.graphics.getHeight() * 0.02f) - FRConstants.C_BUTTON_SIZE) * 0.05f / 2.0f + FRConstants.C_BUTTON_SIZE * 0.3f + (FRConstants.C_BUTTON_SIZE * 1.5f - FRConstants.C_BUTTON_SIZE) / 2.0f, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, false, "", FRAssetManager.getAtlas().createSprite("vk_icon"), true);
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
                        FlowRush.getAndroidHelper().openVK();
                    }
                });
                return vkButton;
            case 11://Актер гекс в игре на надписи wellDone
                float welldoneWidth = FRConstants.C_BUTTON_SIZE * 0.9f;
                float welldoneHeight = FRConstants.C_BUTTON_SIZE * 0.9f * 0.8947368f;
                SmallButtonActor welldoneHex = createSmallButtonActor((Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * 0.6f) / 2.0f - welldoneWidth / 2.0f, Gdx.graphics.getHeight() - welldoneHeight - (FRConstants.C_BUTTON_SIZE - welldoneHeight) / 2.0f, welldoneWidth, welldoneHeight, false, "", FRAssetManager.getAtlas().createSprite("bighex_light"), false);

                RotateByAction rotateToActionWellDone = new RotateByAction();
                rotateToActionWellDone.setDuration(2f);
                rotateToActionWellDone.setAmount(-360);

                RepeatAction repeatActionWellDone = new RepeatAction();
                repeatActionWellDone.setAction(rotateToActionWellDone);
                repeatActionWellDone.setCount(RepeatAction.FOREVER);

                welldoneHex.addAction(repeatActionWellDone);
                return welldoneHex;
            case 12://Кнопка NEXT lvlcomplete
                final SmallButtonActor nextButton = createSmallButtonActor(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 8, 0.0f, Gdx.graphics.getWidth() / 8, Gdx.graphics.getWidth() / 8, false, "", FRAssetManager.getAtlas().createSprite("next_icon"), true);
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
                        FlowRush.getInstance().screenType = FRConstants.ScreenType.GAME;
                        if(nextButton.getName().equals("show pack")){
                            GameScreen.getInstance().showPackComplete();
                            nextButton.setName("");
                        }else {
                            GameScreen.getInstance().startNewLevel();
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
                SmallButtonActor supportUsButton = createSmallButtonActor((Gdx.graphics.getWidth() - FRConstants.C_BUTTON_SIZE) / 2.0f, Gdx.graphics.getHeight() * 0.02f, FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE, true, "", FRAssetManager.getAtlas().createSprite("ads_icon"), true);
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
                        MenuScreen.getInstance().setSupportUsScreen();
                    }
                });
                return supportUsButton;
            case 14://Google Play Games button
                Sprite googlePlaySprite = new Sprite(new Texture(Gdx.files.internal("controller.png")));
                SmallButtonActor googlePlayButton = createSmallButtonActor(Gdx.graphics.getHeight() * 0.02f, Gdx.graphics.getHeight() - FRConstants.C_BUTTON_SIZE, FRConstants.C_BUTTON_SIZE*0.8f, FRConstants.C_BUTTON_SIZE*0.8f, true, "", googlePlaySprite, true);
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
                        if(FlowRush.getPlayServices().isSignedIn()){
                            MenuScreen.getInstance().setSignedScreen();
                        }else {
                            MenuScreen.getInstance().setSignInScreen();
                        }
                    }
                });
                return googlePlayButton ;
            default:
                return null;
        }
    }

    public static BackgroundActor getBackgroundActor(int type, boolean isDuplicate){
        BackgroundActor backgroundActor;
        if (type == 1) {// left -> right
            backgroundActor = new LeftBackgroundActor(FRAssetManager.getStripe(), isDuplicate ? 1f : 1.3f);
        } else if (type == 2) { // down -> top
            backgroundActor = new BottomBackgroundActor(FRAssetManager.getStripe(), isDuplicate ? 2f : 2.6f);
        } else if (type == 3) { // right -> left
            backgroundActor = new RightBackgroundActor(FRAssetManager.getStripe(), isDuplicate ? 1f : 1.3f);
        } else { // top -> down
            backgroundActor = new TopBackgroundActor(FRAssetManager.getStripe(), isDuplicate ? 2f : 2.6f);
        }
        return backgroundActor;
    }
}