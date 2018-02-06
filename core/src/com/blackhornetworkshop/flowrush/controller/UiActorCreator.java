package com.blackhornetworkshop.flowrush.controller;

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
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.controller.listeners.ButtonScaleListener;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;
import com.blackhornetworkshop.flowrush.view.ui.SmallButtonActor;
import com.blackhornetworkshop.flowrush.view.ui.UIPool;
import com.blackhornetworkshop.flowrush.view.ui.background.BackgroundActor;
import com.blackhornetworkshop.flowrush.view.ui.background.BottomBackgroundActor;
import com.blackhornetworkshop.flowrush.view.ui.background.LeftBackgroundActor;
import com.blackhornetworkshop.flowrush.view.ui.background.RightBackgroundActor;
import com.blackhornetworkshop.flowrush.view.ui.background.TopBackgroundActor;

import static com.blackhornetworkshop.flowrush.model.FRConstants.*;

//Created by TScissors. Главный класс игрового экрана

public class UiActorCreator {

    public static TextButton getTextButton(int type) { //актеры кнопки с текстом
        TextButton textButton;
        switch (type) {
            case 1: //PLAY button
                textButton = createTextButton("PLAY", "playbutton", PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT, PLAY_BUTTON_X, PLAY_BUTTON_Y, true, true);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        ScreenManager.setGameMainScreen();
                    }
                });
                return textButton;
            case 2://LEVELS button
                textButton = createTextButton("LEVELS", "lightblue", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, LEVELS_BUTTON_Y, true, true);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        ScreenManager.setMenuPackChoiceScreen();
                    }
                });
                return textButton;
            case 3: //FEEDBACK button
                textButton = createTextButton("SEND FEEDBACK", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_1, false, true);
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
                return textButton;
            case 4: //RATE button
                textButton = createTextButton("RATE", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_2, false, true);
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
                return textButton;
            case 5: //SUPPORT US button (authors screen)
                textButton = createTextButton("SUPPORT US", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_3, false, true);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        ScreenManager.setMenuSupportScreen();
                    }
                });
                return textButton;
            case 6: //REMOVE ADS button
                textButton = createTextButton("REMOVE ADS", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, REMOVE_ADS_BUTTON_Y, true, true);
                return textButton;
            case 7: //MENU label
                textButton = createTextButton("", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_LABEL_Y, false, false);
                textButton.setVisible(false);
                return textButton;
            case 8: //SOCIAL NETWORKS background
                textButton = createTextButton("", "white", MENU_BUTTON_WIDTH, SOCIAL_BACKGROUND_HEIGHT, MENU_BUTTON_X, MENU_BUTTON_Y_1, false, false);
                textButton.setVisible(false);
                return textButton;
            case 9: //MENU PACKCOMPLETE button
                textButton = createTextButton("MENU", "default", MENU_PACK_COMPLETE_BUTTON_WIDTH, MENU_PACK_COMPLETE_BUTTON_HEIGHT, MENU_PACK_COMPLETE_BUTTON_X, MENU_PACK_COMPLETE_BUTTON_Y, false, true);
                textButton.addListener(new ButtonScaleListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return textButton;
            case 10: //RIGHT button
                textButton = createTextButton("YES!", "whitesmall", RATE_DIALOG_BUTTON_WIDTH, RATE_DIALOG_BUTTON_HEIGHT, RATE_US_DIALOG_BUTTON_RIGHT_X, RATE_US_DIALOG_BUTTON_Y, false, true);
                return textButton;
            case 11: //LEFT button
                textButton = createTextButton("NOT SURE", "bordersmall", RATE_DIALOG_BUTTON_WIDTH, RATE_DIALOG_BUTTON_HEIGHT, RATE_US_DIALOG_BUTTON_LEFT_X, RATE_US_DIALOG_BUTTON_Y, false, true);
                return textButton;
            case 12: //NEXT PACK button
                textButton = createTextButton("NEXT", "darkblue", NEXT_PACK_BUTTON_WIDTH, BUTTON_SIZE, NEXT_PACK_BUTTON_X, NEXT_PACK_BUTTON_Y, false, true);
                textButton.addListener(new ButtonScaleListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        ScreenManager.setGameMainScreen();
                    }
                });
                return textButton;
            case 13://EXIT button
                textButton = createTextButton("EXIT", "lightblue", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, EXIT_BUTTON_Y, false, true);
                textButton.addListener(new ButtonScaleListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.exit();
                    }
                });
                return textButton;
            case 14: //SIGN IN button
                textButton = createTextButton("SIGN IN", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_1, false, true);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        FlowRush.logDebug("Sign In button was pressed");
                        FlowRush.getPlayServices().signIn();
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return textButton;
            case 15: //SNAPSHOTS button
                textButton = createTextButton("LOAD GAME", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_3, false, true);
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
                return textButton;
            case 16: //ACHIEVEMENTS button
                textButton = createTextButton("ACHIEVEMENTS", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_2, false, true);
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
                return textButton;
            case 17: //SIGN OUT button
                textButton = createTextButton("SIGN OUT", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_1, false, true);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        FlowRush.logDebug("Sign Out button was pressed");
                        FlowRush.getPlayServices().signOut();
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return textButton;
            default:
                textButton = null;
                return textButton;
        }
    }

    private static TextButton createTextButton(String text, String styleName, float width, float height, float x, float y, boolean isVisible, boolean isTransformable){
        TextButton textButton = new TextButton(text, FRAssetManager.getSkin(), styleName);
        textButton.setBounds(x, y, width, height);
        textButton.setVisible(isVisible);
        textButton.setTransform(isTransformable);
        textButton.setOrigin(Align.center);
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
                string = "FLYING";
                break;
            case 5:
                string = "SOURCE\ncoming soon";
                break;
            default:
                string = "";
                break;
        }

        float up = (Gdx.graphics.getHeight() * 0.98f - BUTTON_SIZE); //верхний отступ
        float down = (BUTTON_SIZE) + Gdx.graphics.getHeight() * 0.02f; //нижний отступ
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
            case 1://PAUSE button
                SmallButtonActor pauseButton= createSmallButtonActor(0.0f, 0.0f, Gdx.graphics.getWidth() / 8, Gdx.graphics.getWidth() / 8, true, FRAssetManager.getAtlas().createSprite("pause_icon"), true);
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
                        if (ScreenManager.getCurrentScreen() != ScreenType.GAME_LVL_COMPLETE) {
                            UIPool.getPauseBackground().setVisible(true);
                        }
                        ScreenManager.setGamePauseScreen();
                    }
                });
                return pauseButton;
            case 2: //RESUME button
                SmallButtonActor backButton = createSmallButtonActor(0.0f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getAtlas().createSprite("back_icon"), true);
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
                        if(ScreenManager.getCurrentScreen() == ScreenType.GAME_PAUSE) {
                            ScreenManager.setGameMainScreen();
                        }else if(ScreenManager.getCurrentScreen() == ScreenType.GAME_LVL_COMPLETE_PAUSE) {
                            ScreenManager.setGameLevelCompleteScreen();
                        }
                    }
                });
                return backButton;
            case 3://Иконка рестарта уровня
                SmallButtonActor restartButton = createSmallButtonActor(BUTTON_SIZE + SCREEN_HEIGHT * 0.05f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getAtlas().createSprite("restart_icon"), false);

                restartButton.addListener(new ButtonScaleListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if(ScreenManager.getCurrentScreen() == ScreenType.GAME_LVL_COMPLETE_PAUSE){
                            LevelLoader.getInstance().prevLvl();
                        }else {
                            LevelLoader.getInstance().reloadActorList();
                        }
                        GameScreen.getInstance().startNewLevel();
                        ScreenManager.setGameMainScreen();
                    }
                });
                return restartButton;
            case 4://Иконка перехода в главное меню из игры
                SmallButtonActor mainMenuButton = createSmallButtonActor(BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getAtlas().createSprite("mmenu_icon"), true);
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
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return mainMenuButton;
            case 5://Иконка контроля звука
                SmallButtonActor soundButton;
                if (FlowRush.getInstance().prefs.isSoundOn()) {
                    soundButton = createSmallButtonActor(0.0f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSoundOn(), true);
                }
                else {
                    soundButton = createSmallButtonActor(0.0f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSoundOff(), true);
                }

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
                            ((SmallButtonActor)event.getListenerActor()).setSprite(FRAssetManager.getSoundOff());
                            FlowRush.getInstance().prefs.setSound(false);
                            FRAssetManager.getBackgroundMusic().pause();
                            FlowRush.getInstance().savePrefsFile();
                            FlowRush.logDebug("Sound is off");
                        } else if (!FlowRush.getInstance().prefs.isSoundOn()) {
                            ((SmallButtonActor)event.getListenerActor()).setSprite(FRAssetManager.getSoundOn());
                            FlowRush.getInstance().prefs.setSound(true);
                            FRAssetManager.getBackgroundMusic().play();
                            FlowRush.getInstance().savePrefsFile();
                            FlowRush.logDebug("Sound is on");
                        }
                    }
                });
                return soundButton;
            case 6://Иконка закрыть в главном меню
                SmallButtonActor closeButton = createSmallButtonActor((Gdx.graphics.getWidth() - BUTTON_SIZE) / 2.0f, Gdx.graphics.getHeight() * 0.02f, BUTTON_SIZE, BUTTON_SIZE, false, FRAssetManager.getAtlas().createSprite("close_icon"), true);
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
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return closeButton;
            case 7://AUTHORS button
                SmallButtonActor authorsButton = createSmallButtonActor(Gdx.graphics.getWidth() - BUTTON_SIZE - Gdx.graphics.getHeight() * 0.02f, Gdx.graphics.getHeight() * 0.02f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getAtlas().createSprite("authors_icon"), true);
                authorsButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        ScreenManager.setMenuAuthorsScreen();
                    }
                });
                return authorsButton;
            case 8://Кнопка Facebook
                SmallButtonActor fbButton = createSmallButtonActor((SCREEN_WIDTH- BUTTON_SIZE) / 2.0f - BUTTON_SIZE * 1.25f, SOCIAL_BUTTON_Y, BUTTON_SIZE, BUTTON_SIZE, false, FRAssetManager.getAtlas().createSprite("fb_icon"), true);
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
                SmallButtonActor twButton = createSmallButtonActor((SCREEN_WIDTH - BUTTON_SIZE) / 2.0f, SOCIAL_BUTTON_Y, BUTTON_SIZE, BUTTON_SIZE, false, FRAssetManager.getAtlas().createSprite("tw_icon"), true);
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
                SmallButtonActor vkButton = createSmallButtonActor((SCREEN_WIDTH - BUTTON_SIZE) / 2.0f + BUTTON_SIZE * 1.25f, SOCIAL_BUTTON_Y, BUTTON_SIZE, BUTTON_SIZE, false, FRAssetManager.getAtlas().createSprite("vk_icon"), true);
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
            case 11://WELLDONE HEX actor
                float welldoneWidth = BUTTON_SIZE * 0.9f;
                float welldoneHeight = BUTTON_SIZE * 0.9f * 0.8947368f;
                SmallButtonActor welldoneHex = createSmallButtonActor((SCREEN_WIDTH - SCREEN_WIDTH * 0.6f) / 2.0f - welldoneWidth / 2.0f, SCREEN_HEIGHT - welldoneHeight - (BUTTON_SIZE - welldoneHeight) / 2.0f, welldoneWidth, welldoneHeight, false, FRAssetManager.getAtlas().createSprite("bighex_light"), false);

                RotateByAction rotateToActionWellDone = new RotateByAction();
                rotateToActionWellDone.setDuration(2f);
                rotateToActionWellDone.setAmount(-360);

                RepeatAction repeatActionWellDone = new RepeatAction();
                repeatActionWellDone.setAction(rotateToActionWellDone);
                repeatActionWellDone.setCount(RepeatAction.FOREVER);

                welldoneHex.addAction(repeatActionWellDone);
                return welldoneHex;
            case 12://NEXT button (pack or level)
                final SmallButtonActor nextButton = createSmallButtonActor(SCREEN_WIDTH - SCREEN_WIDTH/ 8, 0.0f, SCREEN_WIDTH/ 8, SCREEN_WIDTH / 8, false, FRAssetManager.getAtlas().createSprite("next_icon"), true);
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
                        if(LevelLoader.getInstance().containsNext()){
                            GameScreen.getInstance().startNewLevel();
                            ScreenManager.setGameMainScreen();
                        }else{
                            ScreenManager.setGamePackCompleteScreen();
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
            case 13://SUPPORT US button
                SmallButtonActor supportUsButton = createSmallButtonActor((SCREEN_WIDTH - BUTTON_SIZE) / 2.0f, SCREEN_HEIGHT * 0.02f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getAtlas().createSprite("ads_icon"), true);
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
                        ScreenManager.setMenuSupportScreen();
                    }
                });
                return supportUsButton;
            case 14://Google Play Games button
                Sprite googlePlaySprite = new Sprite(new Texture(Gdx.files.internal("controller.png")));
                SmallButtonActor googlePlayButton = createSmallButtonActor(SCREEN_HEIGHT * 0.02f, SCREEN_HEIGHT - BUTTON_SIZE, BUTTON_SIZE*0.8f, BUTTON_SIZE*0.8f, true, googlePlaySprite, true);
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
                            ScreenManager.setMenuGoogleSignedScreen();
                        }else {
                            ScreenManager.setMenuGoogleSignInScreen();
                        }
                    }
                });
                return googlePlayButton ;
            default:
                return null;
        }
    }

    private static SmallButtonActor createSmallButtonActor(float x, float y, float width, float height, boolean visible, Sprite sprite, boolean isScalable) {
        final SmallButtonActor smallButtonActor = new SmallButtonActor();
        smallButtonActor.setBounds(x, y, width, height);
        smallButtonActor.setOrigin(width / 2.0f, height / 2.0f);
        smallButtonActor.setVisible(visible);
        smallButtonActor.setSprite(sprite);
        if (isScalable) {
            smallButtonActor.addListener(new ButtonScaleListener());
        }
        return smallButtonActor;
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