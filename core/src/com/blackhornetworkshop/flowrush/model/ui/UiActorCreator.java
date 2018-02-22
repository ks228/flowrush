package com.blackhornetworkshop.flowrush.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.controller.LevelController;
import com.blackhornetworkshop.flowrush.controller.RateDialogController;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.model.FRFileHandler;
import com.blackhornetworkshop.flowrush.model.FlowRush;
import com.blackhornetworkshop.flowrush.model.ex.FlowRushException;
import com.blackhornetworkshop.flowrush.model.listeners.ButtonScaleListener;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;
import com.blackhornetworkshop.flowrush.model.ui.background.BackgroundActor;
import com.blackhornetworkshop.flowrush.model.ui.background.BottomBackgroundActor;
import com.blackhornetworkshop.flowrush.model.ui.background.LeftBackgroundActor;
import com.blackhornetworkshop.flowrush.model.ui.background.RightBackgroundActor;
import com.blackhornetworkshop.flowrush.model.ui.background.TopBackgroundActor;

import static com.blackhornetworkshop.flowrush.model.FRConstants.*;

//Created by TScissors.

class UiActorCreator {

    static TextButton getTextButton(int type) {
        TextButton textButton;
        switch (type) {
            case 1: //PLAY button
                textButton = createTextButton("PLAY", TEXT_BUTTON_STYLE_PLAYBUTTON, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT, PLAY_BUTTON_X, PLAY_BUTTON_Y, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        LevelController.setCurrentLevel(FlowRush.getSave().getCurrentPack(), FlowRush.getSave().getCurrentLvl());
                        ScreenManager.setGameMainScreen();
                    }
                });
                return textButton;
            case 2://LEVELS button
                textButton = createTextButton("LEVELS", TEXT_BUTTON_STYLE_LIGHTBLUE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, LEVELS_BUTTON_Y, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setMenuPackChoiceScreen();
                    }
                });
                return textButton;
            case 3: //FEEDBACK button
                textButton = createTextButton("SEND FEEDBACK", TEXT_BUTTON_STYLE_WHITE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_1, false);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getAndroidHelper().sendMail();
                    }
                });
                return textButton;
            case 4: //RATE button
                textButton = createTextButton("RATE", TEXT_BUTTON_STYLE_WHITE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_2, false);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getAndroidHelper().openPlayMarket();
                    }
                });
                return textButton;
            case 5: //SUPPORT US button (authors screen)
                textButton = createTextButton("SUPPORT US", TEXT_BUTTON_STYLE_WHITE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_3, false);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setMenuSupportScreen();
                    }
                });
                return textButton;
            case 6: //REMOVE ADS button
                textButton = createTextButton("REMOVE ADS", TEXT_BUTTON_STYLE_WHITE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_2, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {

                    }
                });
                return textButton;
            case 7: //MENU label
                textButton = createTextButton("", TEXT_BUTTON_STYLE_WHITE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_LABEL_Y, false);
                textButton.setVisible(false);
                return textButton;
            case 8: //SOCIAL NETWORKS button
                textButton = createTextButton("JOIN US", TEXT_BUTTON_STYLE_WHITE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_1, false);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getAndroidHelper().openSocialNetworksActivity();
                    }
                });
                return textButton;
            case 9: //MENU button (pack complete)
                textButton = createTextButton("MENU", TEXT_BUTTON_STYLE_DEFAULT, MENU_PACK_COMPLETE_BUTTON_WIDTH, MENU_PACK_COMPLETE_BUTTON_HEIGHT, MENU_PACK_COMPLETE_BUTTON_X, MENU_PACK_COMPLETE_BUTTON_Y, false);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return textButton;
            case 10: //RIGHT button
                textButton = createTextButton(ANSWER_YES_1, TEXT_BUTTON_STYLE_WHITESMALL, RATE_DIALOG_BUTTON_WIDTH, RATE_DIALOG_BUTTON_HEIGHT, RATE_US_DIALOG_BUTTON_RIGHT_X, RATE_US_DIALOG_BUTTON_Y, false);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        if (RateDialogController.isFirstAnswer()) {
                            RateDialogController.setIsFirstAnswerWasYes(true);
                        } else {
                            GameScreen.hideRateDialog();
                            if (RateDialogController.isIsFirstAnswerWasYes()) {
                                FlowRush.getAndroidHelper().openPlayMarket();
                                FlowRush.getPreferences().setShowRateDialog(false);
                                FRFileHandler.savePreferences();
                            } else {
                                FlowRush.getAndroidHelper().sendMail();
                            }
                        }
                    }
                });
                return textButton;
            case 11: //LEFT button
                textButton = createTextButton(ANSWER_NO_1, TEXT_BUTTON_STYLE_BORDERSMALL, RATE_DIALOG_BUTTON_WIDTH, RATE_DIALOG_BUTTON_HEIGHT, RATE_US_DIALOG_BUTTON_LEFT_X, RATE_US_DIALOG_BUTTON_Y, false);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        if (RateDialogController.isFirstAnswer()) {
                            RateDialogController.setIsFirstAnswerWasYes(false);
                        } else {
                            if (!RateDialogController.isIsFirstAnswerWasYes()) {
                                FlowRush.getPreferences().setShowRateDialog(false);
                                FRFileHandler.savePreferences();
                            }
                            GameScreen.hideRateDialog();
                        }
                    }
                });
                return textButton;
            case 12: //NEXT PACK button
                textButton = createTextButton("NEXT", TEXT_BUTTON_STYLE_DARKBLUE, NEXT_PACK_BUTTON_WIDTH, BUTTON_SIZE, NEXT_PACK_BUTTON_X, NEXT_PACK_BUTTON_Y, false);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        GameScreen.getInstance().startNewLevel();
                        ScreenManager.setGameMainScreen();
                    }
                });
                return textButton;
            case 13://EXIT button
                textButton = createTextButton("EXIT", TEXT_BUTTON_STYLE_LIGHTBLUE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, EXIT_BUTTON_Y, false);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        Gdx.app.exit();
                    }
                });
                return textButton;
            case 14: //SIGN IN button
                textButton = createTextButton("SIGN IN", TEXT_BUTTON_STYLE_WHITE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_1, false);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        if(FlowRush.getAndroidHelper().isInternetConnected()) {
                            FlowRush.getPlayServices().signIn();
                            ScreenManager.setMenuMainScreen();
                        }else{
                            FlowRush.getAndroidHelper().showToast("You are currently offline");
                        }
                    }
                });
                return textButton;
            case 15: //SNAPSHOTS button
                textButton = createTextButton("LOAD GAME", TEXT_BUTTON_STYLE_WHITE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_3, false);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        if(FlowRush.getPlayServices().isSignedIn()) {
                            if(FlowRush.getAndroidHelper().isInternetConnected()){
                                FlowRush.getPlayServices().showSavedSnapshots();
                            }else{
                                FlowRush.getAndroidHelper().showToast("You are currently offline");
                            }
                        }else{
                            FlowRush.getAndroidHelper().showToast("You must first sign in");
                        }
                    }
                });
                return textButton;
            case 16: //ACHIEVEMENTS button
                textButton = createTextButton("ACHIEVEMENTS", TEXT_BUTTON_STYLE_WHITE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_2, false);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        if(FlowRush.getPlayServices().isSignedIn()) {
                            FlowRush.getPlayServices().showAchievements();
                        }
                    }
                });
                return textButton;
            case 17: //SIGN OUT button
                textButton = createTextButton("SIGN OUT", TEXT_BUTTON_STYLE_WHITE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_1, false);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getPlayServices().signOut();
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return textButton;
            case 18: //WEBSITE button
                textButton = createTextButton("BLACKHOR.NET", TEXT_BUTTON_STYLE_LIGHTBLUE, MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_2, false);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getAndroidHelper().openWebsite();
                    }
                });
                return textButton;
            default:
                return null;
        }
    }

    private static TextButton createTextButton(String text, String styleName, float width, float height, float x, float y, boolean isVisible) {
        TextButton textButton = new TextButton(text, FRAssetManager.getTextButtonStyle(styleName));
        textButton.setBounds(x, y, width, height);
        textButton.setVisible(isVisible);
        textButton.setOrigin(Align.center);
        textButton.setTransform(true);
        return textButton;
    }

    static TextButton getPackButton(int pack) {
        TextButton textButton = new TextButton("", FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_DARKBLUE));
        textButton.addListener(new ButtonScaleListener(true) {
            @Override
            public void action(InputEvent event) {
                ScreenManager.setMenuLevelChoiceScreen(pack);
            }
        });

        String packName = LevelController.getPackName(pack);

        textButton.setText(packName);
        textButton.setSize(SCREEN_WIDTH * 0.8f, PACK_BUTTON_HEIGHT);
        textButton.setPosition((SCREEN_WIDTH - textButton.getWidth()) / 2, PACK_GROUP_TOP_MARGIN -
                ((PACK_GROUP_TOP_MARGIN - PACK_GROUP_BOTTOM_MARGIN) - PACK_BUTTON_HEIGHT * 5 - PACK_BUTTON_HEIGHT * 0.1f * 4) / 2 -
                PACK_BUTTON_HEIGHT * 0.1f * (pack - 1) - PACK_BUTTON_HEIGHT * pack);
        textButton.setOrigin(Align.center);
        textButton.setTransform(true);

        return textButton;
    }

    static SmallButtonActor getSmallButtonActor(int type) { //Маленькие кнопки актеры
        switch (type) {
            case 1://PAUSE button
                SmallButtonActor pauseButton = createSmallButtonActor(0.0f, 0.0f, SCREEN_WIDTH / 8, SCREEN_WIDTH / 8, true, FRAssetManager.getSprite(PAUSE_ICON));
                pauseButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        if (ScreenManager.getCurrentScreen() != ScreenType.GAME_LVL_COMPLETE) {
                            UIPool.getPauseBackground().setVisible(true);
                        }
                        ScreenManager.setGamePauseScreen();
                    }
                });
                return pauseButton;
            case 2: //RESUME button
                SmallButtonActor backButton = createSmallButtonActor(0.0f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSprite(BACK_BUTTON_ICON));
                backButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        if (ScreenManager.getCurrentScreen() == ScreenType.GAME_PAUSE) {
                            ScreenManager.setGameMainScreen();
                        } else if (ScreenManager.getCurrentScreen() == ScreenType.GAME_LVL_COMPLETE_PAUSE) {
                            ScreenManager.setGameLevelCompleteScreen();
                        }
                    }
                });
                return backButton;
            case 3://RESTART button
                SmallButtonActor restartButton = createSmallButtonActor(BUTTON_SIZE + SCREEN_HEIGHT * 0.05f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSprite(RESTART_ICON));
                restartButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        if (ScreenManager.getCurrentScreen() == ScreenType.GAME_LVL_COMPLETE_PAUSE) {
                            LevelController.prevLvl();
                        } else {
                            LevelController.reloadActorList();
                        }
                        GameScreen.getInstance().startNewLevel();
                        ScreenManager.setGameMainScreen();
                    }
                });

                return restartButton;
            case 4://MENU button (pause group)
                SmallButtonActor mainMenuButton = createSmallButtonActor(BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSprite(MENU_ICON));
                mainMenuButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return mainMenuButton;
            case 5://SOUND button
                SmallButtonActor soundButton;
                if (FlowRush.getPreferences().isSoundOn()) {
                    soundButton = createSmallButtonActor(0.0f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSprite(SOUND_ON_ICON));
                } else {
                    soundButton = createSmallButtonActor(0.0f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSprite(SOUND_OFF_ICON));
                }
                soundButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getPreferences().setSound(!FlowRush.getPreferences().isSoundOn());
                        FRFileHandler.savePreferences();
                        if (FlowRush.getPreferences().isSoundOn()) {
                            ((SmallButtonActor) event.getListenerActor()).setSprite(FRAssetManager.getSprite(SOUND_ON_ICON));
                            FRAssetManager.getBackgroundMusic().play();
                            FlowRush.logDebug("Sound is on");
                        } else {
                            ((SmallButtonActor) event.getListenerActor()).setSprite(FRAssetManager.getSprite(SOUND_OFF_ICON));
                            FRAssetManager.getBackgroundMusic().pause();
                            FlowRush.logDebug("Sound is off");

                        }
                    }
                });
                return soundButton;
            case 6://CLOSE button (menu screen)
                SmallButtonActor closeButton = createSmallButtonActor((SCREEN_WIDTH - BUTTON_SIZE) / 2.0f, SCREEN_HEIGHT * 0.02f, BUTTON_SIZE, BUTTON_SIZE, false, FRAssetManager.getSprite(CLOSE_ICON));
                closeButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return closeButton;
            case 7://AUTHORS button
                SmallButtonActor authorsButton = createSmallButtonActor(SCREEN_WIDTH - BUTTON_SIZE - SCREEN_HEIGHT * 0.02f, SCREEN_HEIGHT * 0.02f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSprite(AUTHORS_ICON));
                authorsButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setMenuAuthorsScreen();
                    }
                });
                return authorsButton;
            case 11://WELLDONE HEX actor
                float welldoneWidth = BUTTON_SIZE * 0.9f;
                float welldoneHeight = BUTTON_SIZE * 0.9f * 0.8947368f;
                SmallButtonActor welldoneHex = createSmallButtonActor((SCREEN_WIDTH - SCREEN_WIDTH * 0.6f) / 2.0f - welldoneWidth / 2.0f, SCREEN_HEIGHT - welldoneHeight - (BUTTON_SIZE - welldoneHeight) / 2.0f, welldoneWidth, welldoneHeight, false, FRAssetManager.getSprite(BIG_HEX_LIGHT));

                RotateByAction rotateToActionWellDone = new RotateByAction();
                rotateToActionWellDone.setDuration(2f);
                rotateToActionWellDone.setAmount(-360);

                RepeatAction repeatActionWellDone = new RepeatAction();
                repeatActionWellDone.setAction(rotateToActionWellDone);
                repeatActionWellDone.setCount(RepeatAction.FOREVER);

                welldoneHex.addAction(repeatActionWellDone);
                return welldoneHex;
            case 12://NEXT button (pack or level)
                final SmallButtonActor nextButton = createSmallButtonActor(SCREEN_WIDTH - SCREEN_WIDTH / 8, 0.0f, SCREEN_WIDTH / 8, SCREEN_WIDTH / 8, false, FRAssetManager.getSprite(NEXT_ICON));
                nextButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        if (!GameScreen.showPackComplete) {
                            GameScreen.getInstance().startNewLevel();
                            ScreenManager.setGameMainScreen();
                        } else {
                            ScreenManager.setGamePackCompleteScreen();
                        }
                    }
                });
                MoveToAction moveToAction1 = new MoveToAction();
                moveToAction1.setDuration(0.5f);
                moveToAction1.setPosition(SCREEN_WIDTH - nextButton.getWidth() * 1.3f, 0);

                MoveToAction moveToAction2 = new MoveToAction();
                moveToAction2.setDuration(0.5f);
                moveToAction2.setPosition(SCREEN_WIDTH - nextButton.getWidth(), 0);

                SequenceAction seqActionNextButton = new SequenceAction(moveToAction1, moveToAction2);

                RepeatAction repeatActionNext = new RepeatAction();
                repeatActionNext.setAction(seqActionNextButton);
                repeatActionNext.setCount(RepeatAction.FOREVER);

                nextButton.addAction(repeatActionNext);
                return nextButton;
            case 13://SUPPORT US button
                SmallButtonActor supportUsButton = createSmallButtonActor((SCREEN_WIDTH - BUTTON_SIZE) / 2.0f, SCREEN_HEIGHT * 0.02f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSprite(ADS_ICON));
                supportUsButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setMenuSupportScreen();
                    }
                });
                return supportUsButton;
            case 14://GOOGLE GAMES button
                SmallButtonActor googlePlayButton = createSmallButtonActor(SCREEN_HEIGHT * 0.03f, SCREEN_HEIGHT - BUTTON_SIZE - SCREEN_HEIGHT * 0.02f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSprite(CONTROLLER_ICON));
                googlePlayButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        if (FlowRush.getPlayServices().isSignedIn()) {
                            ScreenManager.setMenuGoogleSignedScreen();
                        } else {
                            ScreenManager.setMenuGoogleSignInScreen();
                        }
                    }
                });
                return googlePlayButton;
            case 15: // DAY NIGHT button
                SmallButtonActor dayNightButton = createSmallButtonActor(SCREEN_WIDTH - BUTTON_SIZE - SCREEN_HEIGHT * 0.02f, SCREEN_HEIGHT - BUTTON_SIZE - SCREEN_HEIGHT * 0.02f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSprite(DAY_NIGHT_ICON));
                dayNightButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getPreferences().setNightMode(!FlowRush.getPreferences().isNightMode());
                        FRFileHandler.savePreferences();
                        FlowRush.dayNightShift();
                    }
                });
                return dayNightButton;
            default:
                return null;
        }

    }

    private static SmallButtonActor createSmallButtonActor(float x, float y, float width, float height, boolean visible, Sprite sprite) {
        final SmallButtonActor smallButtonActor = new SmallButtonActor();
        smallButtonActor.setBounds(x, y, width, height);
        smallButtonActor.setOrigin(width / 2.0f, height / 2.0f);
        smallButtonActor.setVisible(visible);
        smallButtonActor.setSprite(sprite);
        return smallButtonActor;
    }

    static BackgroundActor getBackgroundActor(int type, boolean isDuplicate) {
        BackgroundActor backgroundActor;
        if (type == 1) {// left -> right
            backgroundActor = new LeftBackgroundActor(FRAssetManager.getBackgroundStripe(), isDuplicate ? 1f : 1.3f);
        } else if (type == 2) { // down -> top
            backgroundActor = new BottomBackgroundActor(FRAssetManager.getBackgroundStripe(), isDuplicate ? 2f : 2.6f);
        } else if (type == 3) { // right -> left
            backgroundActor = new RightBackgroundActor(FRAssetManager.getBackgroundStripe(), isDuplicate ? 1f : 1.3f);
        } else { // top -> down
            backgroundActor = new TopBackgroundActor(FRAssetManager.getBackgroundStripe(), isDuplicate ? 2f : 2.6f);
        }
        return backgroundActor;
    }
}