package com.blackhornetworkshop.flowrush.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.controller.RateDialogController;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.model.listeners.ButtonScaleListener;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;
import com.blackhornetworkshop.flowrush.model.ui.SmallButtonActor;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;
import com.blackhornetworkshop.flowrush.model.ui.background.BackgroundActor;
import com.blackhornetworkshop.flowrush.model.ui.background.BottomBackgroundActor;
import com.blackhornetworkshop.flowrush.model.ui.background.LeftBackgroundActor;
import com.blackhornetworkshop.flowrush.model.ui.background.RightBackgroundActor;
import com.blackhornetworkshop.flowrush.model.ui.background.TopBackgroundActor;

import static com.blackhornetworkshop.flowrush.model.FRConstants.*;

//Created by TScissors.

public class UiActorCreator {

    public static TextButton getTextButton(int type) {
        TextButton textButton;
        switch (type) {
            case 1: //PLAY button
                textButton = createTextButton("PLAY", "playbutton", PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT, PLAY_BUTTON_X, PLAY_BUTTON_Y, true, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setGameMainScreen();
                    }
                });
                return textButton;
            case 2://LEVELS button
                textButton = createTextButton("LEVELS", "lightblue", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, LEVELS_BUTTON_Y, true, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setMenuPackChoiceScreen();
                    }
                });
                return textButton;
            case 3: //FEEDBACK button
                textButton = createTextButton("SEND FEEDBACK", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_1, false, true);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getAndroidHelper().sendMail();
                    }
                });
                return textButton;
            case 4: //RATE button
                textButton = createTextButton("RATE", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_2, false, true);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getAndroidHelper().openPlayMarket();
                    }
                });
                return textButton;
            case 5: //SUPPORT US button (authors screen)
                textButton = createTextButton("SUPPORT US", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_3, false, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
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
            case 9: //PACK COMPLETE MENU button
                textButton = createTextButton("MENU", "default", MENU_PACK_COMPLETE_BUTTON_WIDTH, MENU_PACK_COMPLETE_BUTTON_HEIGHT, MENU_PACK_COMPLETE_BUTTON_X, MENU_PACK_COMPLETE_BUTTON_Y, false, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return textButton;
            case 10: //RIGHT button
                textButton = createTextButton("YES!", "whitesmall", RATE_DIALOG_BUTTON_WIDTH, RATE_DIALOG_BUTTON_HEIGHT, RATE_US_DIALOG_BUTTON_RIGHT_X, RATE_US_DIALOG_BUTTON_Y, false, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        if (RateDialogController.isFirstAnswer()) {
                            RateDialogController.setIsFirstAnswerWasYes(true);
                        } else {
                            GameScreen.hideRateDialog();
                            if(RateDialogController.isIsFirstAnswerWasYes()){
                                FlowRush.getAndroidHelper().openPlayMarket();
                                FlowRush.getPreferences().setShowRateDialog(false);
                                FRFileHandler.savePreferences();
                            }else{
                                FlowRush.getAndroidHelper().sendMail();
                            }
                        }
                    }
                });
                return textButton;
            case 11: //LEFT button
                textButton = createTextButton("NOT SURE", "bordersmall", RATE_DIALOG_BUTTON_WIDTH, RATE_DIALOG_BUTTON_HEIGHT, RATE_US_DIALOG_BUTTON_LEFT_X, RATE_US_DIALOG_BUTTON_Y, false, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        if (RateDialogController.isFirstAnswer()) {
                            RateDialogController.setIsFirstAnswerWasYes(false);
                        } else {
                            if(!RateDialogController.isIsFirstAnswerWasYes()){
                                FlowRush.getPreferences().setShowRateDialog(false);
                                FRFileHandler.savePreferences();
                            }
                            GameScreen.hideRateDialog();
                        }
                    }
                });
                return textButton;
            case 12: //NEXT PACK button
                textButton = createTextButton("NEXT", "darkblue", NEXT_PACK_BUTTON_WIDTH, BUTTON_SIZE, NEXT_PACK_BUTTON_X, NEXT_PACK_BUTTON_Y, false, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        ScreenManager.setGameMainScreen();
                    }
                });
                return textButton;
            case 13://EXIT button
                textButton = createTextButton("EXIT", "lightblue", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, EXIT_BUTTON_Y, false, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        Gdx.app.exit();
                    }
                });
                return textButton;
            case 14: //SIGN IN button
                textButton = createTextButton("SIGN IN", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_1, false, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getPlayServices().signIn();
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return textButton;
            case 15: //SNAPSHOTS button
                textButton = createTextButton("LOAD GAME", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_3, false, true);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getPlayServices().showSavedSnapshots();
                    }
                });
                return textButton;
            case 16: //ACHIEVEMENTS button
                textButton = createTextButton("ACHIEVEMENTS", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_2, false, true);
                textButton.addListener(new ButtonScaleListener(false) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getPlayServices().showAchievements();
                    }
                });
                return textButton;
            case 17: //SIGN OUT button
                textButton = createTextButton("SIGN OUT", "white", MENU_BUTTON_WIDTH, BUTTON_SIZE, MENU_BUTTON_X, MENU_BUTTON_Y_1, false, true);
                textButton.addListener(new ButtonScaleListener(true) {
                    @Override
                    public void action(InputEvent event) {
                        FlowRush.getPlayServices().signOut();
                        ScreenManager.setMenuMainScreen();
                    }
                });
                return textButton;
            default:
                return null;
        }
    }

    private static TextButton createTextButton(String text, String styleName, float width, float height, float x, float y, boolean isVisible, boolean isTransformable) {
        TextButton textButton = new TextButton(text, FRAssetManager.getSkin(), styleName);
        textButton.setBounds(x, y, width, height);
        textButton.setVisible(isVisible);
        textButton.setTransform(isTransformable);
        textButton.setOrigin(Align.center);
        return textButton;
    }

    public static TextButton getPackButton(int pack) {
        TextButton textButton;

        if (LevelController.getLevelPack(pack - 1).available) {
            textButton = new TextButton("", FRAssetManager.getSkin(), "darkblue");
            textButton.addListener(new ButtonScaleListener(true) {
                @Override
                public void action(InputEvent event) {
                    ScreenManager.setMenuLevelChoiceScreen(pack);
                }
            });
        } else {
            textButton = new TextButton("", FRAssetManager.getSkin(), "alphablackgrey");
            textButton.addListener(new ButtonScaleListener(false));
        }

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

    public static SmallButtonActor getSmallButtonActor(int type) { //Маленькие кнопки актеры
        switch (type) {
            case 1://PAUSE button
                SmallButtonActor pauseButton = createSmallButtonActor(0.0f, 0.0f, Gdx.graphics.getWidth() / 8, Gdx.graphics.getWidth() / 8, true, FRAssetManager.getAtlas().createSprite("pause_icon"));
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
                SmallButtonActor backButton = createSmallButtonActor(0.0f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getAtlas().createSprite("back_icon"));
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
                SmallButtonActor restartButton = createSmallButtonActor(BUTTON_SIZE + SCREEN_HEIGHT * 0.05f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getAtlas().createSprite("restart_icon"));
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
        SmallButtonActor mainMenuButton = createSmallButtonActor(BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getAtlas().createSprite("mmenu_icon"));
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
            soundButton = createSmallButtonActor(0.0f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSoundOn());
        } else {
            soundButton = createSmallButtonActor(0.0f, 0.0f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getSoundOff());
        }
        soundButton.addListener(new ButtonScaleListener(false) {
            @Override
            public void action(InputEvent event) {
                if (FlowRush.getPreferences().isSoundOn()) {
                    ((SmallButtonActor) event.getListenerActor()).setSprite(FRAssetManager.getSoundOff());
                    FlowRush.getPreferences().setSound(false);
                    FRAssetManager.getBackgroundMusic().pause();
                    FRFileHandler.savePreferences();
                    FlowRush.logDebug("Sound is off");
                } else {
                    ((SmallButtonActor) event.getListenerActor()).setSprite(FRAssetManager.getSoundOn());
                    FlowRush.getPreferences().setSound(true);
                    FRAssetManager.getBackgroundMusic().play();
                    FRFileHandler.savePreferences();
                    FlowRush.logDebug("Sound is on");
                }
            }
        });
        return soundButton;
        case 6://CLOSE button (menu screen)
        SmallButtonActor closeButton = createSmallButtonActor((Gdx.graphics.getWidth() - BUTTON_SIZE) / 2.0f, Gdx.graphics.getHeight() * 0.02f, BUTTON_SIZE, BUTTON_SIZE, false, FRAssetManager.getAtlas().createSprite("close_icon"));
        closeButton.addListener(new ButtonScaleListener(true) {
            @Override
            public void action(InputEvent event) {
                ScreenManager.setMenuMainScreen();
            }
        });
        return closeButton;
        case 7://AUTHORS button
        SmallButtonActor authorsButton = createSmallButtonActor(Gdx.graphics.getWidth() - BUTTON_SIZE - Gdx.graphics.getHeight() * 0.02f, Gdx.graphics.getHeight() * 0.02f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getAtlas().createSprite("authors_icon"));
        authorsButton.addListener(new ButtonScaleListener(true) {
            @Override
            public void action(InputEvent event) {
                ScreenManager.setMenuAuthorsScreen();
            }
        });
        return authorsButton;
        case 8://FACEBOOK button
        SmallButtonActor fbButton = createSmallButtonActor((SCREEN_WIDTH - BUTTON_SIZE) / 2.0f - BUTTON_SIZE * 1.25f, SOCIAL_BUTTON_Y, BUTTON_SIZE, BUTTON_SIZE, false, FRAssetManager.getAtlas().createSprite("fb_icon"));
        fbButton.addListener(new ButtonScaleListener(false) {
            @Override
            public void action(InputEvent event) {
                FlowRush.getAndroidHelper().openFacebook();
            }
        });
        return fbButton;
        case 9://TWITTER button
        SmallButtonActor twButton = createSmallButtonActor((SCREEN_WIDTH - BUTTON_SIZE) / 2.0f, SOCIAL_BUTTON_Y, BUTTON_SIZE, BUTTON_SIZE, false, FRAssetManager.getAtlas().createSprite("tw_icon"));
        twButton.addListener(new ButtonScaleListener(false) {
            @Override
            public void action(InputEvent event) {
                FlowRush.getAndroidHelper().openTwitter();
            }
        });
        return twButton;
        case 10://VK button
        SmallButtonActor vkButton = createSmallButtonActor((SCREEN_WIDTH - BUTTON_SIZE) / 2.0f + BUTTON_SIZE * 1.25f, SOCIAL_BUTTON_Y, BUTTON_SIZE, BUTTON_SIZE, false, FRAssetManager.getAtlas().createSprite("vk_icon"));
        vkButton.addListener(new ButtonScaleListener(false) {
            @Override
            public void action(InputEvent event) {
                FlowRush.getAndroidHelper().openVK();
            }
        });
        return vkButton;
        case 11://WELLDONE HEX actor
        float welldoneWidth = BUTTON_SIZE * 0.9f;
        float welldoneHeight = BUTTON_SIZE * 0.9f * 0.8947368f;
        SmallButtonActor welldoneHex = createSmallButtonActor((SCREEN_WIDTH - SCREEN_WIDTH * 0.6f) / 2.0f - welldoneWidth / 2.0f, SCREEN_HEIGHT - welldoneHeight - (BUTTON_SIZE - welldoneHeight) / 2.0f, welldoneWidth, welldoneHeight, false, FRAssetManager.getAtlas().createSprite("bighex_light"));

        RotateByAction rotateToActionWellDone = new RotateByAction();
        rotateToActionWellDone.setDuration(2f);
        rotateToActionWellDone.setAmount(-360);

        RepeatAction repeatActionWellDone = new RepeatAction();
        repeatActionWellDone.setAction(rotateToActionWellDone);
        repeatActionWellDone.setCount(RepeatAction.FOREVER);

        welldoneHex.addAction(repeatActionWellDone);
        return welldoneHex;
        case 12://NEXT button (pack or level)
        final SmallButtonActor nextButton = createSmallButtonActor(SCREEN_WIDTH - SCREEN_WIDTH / 8, 0.0f, SCREEN_WIDTH / 8, SCREEN_WIDTH / 8, false, FRAssetManager.getAtlas().createSprite("next_icon"));
        nextButton.addListener(new ButtonScaleListener(true) {
            @Override
            public void action(InputEvent event) {
                if (LevelController.nextLevelExist()) {
                    GameScreen.getInstance().startNewLevel();
                    ScreenManager.setGameMainScreen();
                } else {
                    ScreenManager.setGamePackCompleteScreen();
                }
            }
        });
        MoveToAction moveToAction1 = new MoveToAction();
        moveToAction1.setDuration(0.5f);
        moveToAction1.setPosition(Gdx.graphics.getWidth() - nextButton.getWidth() * 1.3f, 0);

        MoveToAction moveToAction2 = new MoveToAction();
        moveToAction2.setDuration(0.5f);
        moveToAction2.setPosition(Gdx.graphics.getWidth() - nextButton.getWidth(), 0);

        SequenceAction seqActionNextButton = new SequenceAction(moveToAction1, moveToAction2);

        RepeatAction repeatActionNext = new RepeatAction();
        repeatActionNext.setAction(seqActionNextButton);
        repeatActionNext.setCount(RepeatAction.FOREVER);

        nextButton.addAction(repeatActionNext);
        return nextButton;
        case 13://SUPPORT US button
        SmallButtonActor supportUsButton = createSmallButtonActor((SCREEN_WIDTH - BUTTON_SIZE) / 2.0f, SCREEN_HEIGHT * 0.02f, BUTTON_SIZE, BUTTON_SIZE, true, FRAssetManager.getAtlas().createSprite("ads_icon"));
        supportUsButton.addListener(new ButtonScaleListener(true) {
            @Override
            public void action(InputEvent event) {
                ScreenManager.setMenuSupportScreen();
            }
        });
        return supportUsButton;
        case 14://GOOGLE GAMES button
        Sprite googlePlaySprite = new Sprite(new Texture(Gdx.files.internal("controller.png")));
        SmallButtonActor googlePlayButton = createSmallButtonActor(SCREEN_HEIGHT * 0.02f, SCREEN_HEIGHT - BUTTON_SIZE, BUTTON_SIZE * 0.8f, BUTTON_SIZE * 0.8f, true, googlePlaySprite);
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

    public static BackgroundActor getBackgroundActor(int type, boolean isDuplicate) {
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