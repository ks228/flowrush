package com.blackhornetworkshop.flowrush.model.ui;

//Created by TScissors.

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.model.FRConstants;
import com.blackhornetworkshop.flowrush.model.ui.background.BackgroundActor;
import com.blackhornetworkshop.flowrush.model.FlowRush;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.controller.LevelController;

import static com.blackhornetworkshop.flowrush.model.FRConstants.*;

public class UIPool {

    //----------------------- COMMON

    private static Group backgroundAnimation;
    private static HexBackgroundActor hexBackgroundActor;
    private static SmallButtonActor soundButton;
    private static Label pauseBackground, levelNumberActor;

    //------------------------ MENU

    //Social networks
    private static TextButton socialNetworksButton;

    //Google play
    private static SmallButtonActor googlePlayButton;
    private static TextButton showAchievementsButton;
    private static TextButton showSnapshotsButton;
    private static TextButton signOutButton;
    private static TextButton signInButton;

    //Support us
    private static SmallButtonActor supportUsSmallButton;
    private static TextButton supportUsButton;
    private static TextButton removeAdsButton;
    private static TextButton websiteButton;
    private static TextButton rateUsButton;
    private static TextButton feedButton;

    //Authors
    private static SmallButtonActor authorsButton;

    //Main menu parts
    private static TextButton menuLabel;
    private static Label innerLayout;
    private static Label messageBackground;
    private static Container<Label> labelContainer;
    private static SmallButtonActor closeButton;
    private static Actor triangleBackgroundTop;
    private static Actor triangleBackground;
    private static Group levelNumbersGroup;
    private static SmallButtonActor dayNightButton;

    //Main buttons
    private static TextButton playButton;
    private static TextButton exitButton;
    private static TextButton levelsButton;
    private static Group packGroup;

    //--------------------- GAME_MAIN

    // Pause menu elements
    private static Group pauseGroup;
    private static SmallButtonActor pauseButton;
    private static Actor quadrantPauseBackground;
    private static SmallButtonActor restartButton;
    private static SmallButtonActor menuButton;
    private static SmallButtonActor resumeButton;

    // Level complete elements
    private static Label wellDoneLabel;
    private static SmallButtonActor nextLevelButton;
    private static SmallButtonActor wellDonehex;

    // Pack complete elements
    private static PackCompleteLowerHex packCompleteLowerHex;
    private static PackCompleteTopHex packCompleteUpperHex;
    private static TextButton packCompleteMenuButton;
    private static TextButton packCompleteNextPackButton;
    private static TextButton leftButton, rightButton;
    private static Label dialogBackground;


    public static void reinialize() {
        levelNumberActor.setStyle(FRAssetManager.getLabelStyle(LABEL_STYLE_GREYFONT));

        if (FlowRush.getPreferences().isSoundOn()) {
            soundButton.setSprite(FRAssetManager.getSprite(SOUND_ON_ICON));
        } else {
            soundButton.setSprite(FRAssetManager.getSprite(SOUND_OFF_ICON));
        }

        hexBackgroundActor.setSprite(FRAssetManager.getSprite(BACKGROUND_ANIMATION));

        for (int x = 0; x < backgroundAnimation.getChildren().size; x++) {
            ((BackgroundActor)backgroundAnimation.getChildren().get(x)).setSprite(FRAssetManager.getBackgroundStripe());
        }

        menuLabel.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITE));

        innerLayout.setStyle(FRAssetManager.getLabelStyle(LABEL_STYLE_DEFAULT));
        messageBackground.setStyle(FRAssetManager.getLabelStyle(LABEL_STYLE_DEFAULT));

        closeButton.setSprite(FRAssetManager.getSprite(CLOSE_ICON));

        playButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_PLAYBUTTON));
        levelsButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_LIGHTBLUE));
        exitButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_LIGHTBLUE));

        for(int x = 0; x < packGroup.getChildren().size; x++){
            ((TextButton)packGroup.getChildren().get(x)).setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_DARKBLUE));
        }

        for (int x = 0; x < levelNumbersGroup.getChildren().size; x++) {
            LevelNumberButton levelNumberButton = (LevelNumberButton)levelNumbersGroup.getChildren().get(x);
            levelNumberButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_LIGHTBLUE));
            levelNumberButton.setLockSprite(FRAssetManager.getSprite(LOCK_ICON));
        }

        dayNightButton.setSprite(FRAssetManager.getSprite(DAY_NIGHT_ICON));

        socialNetworksButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITE));

        //Support us
        supportUsSmallButton.setSprite(FRAssetManager.getSprite(ADS_ICON));
        supportUsButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITE));
        rateUsButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITE));
        feedButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITE));
        websiteButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_LIGHTBLUE));

        removeAdsButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITE));

        authorsButton.setSprite(FRAssetManager.getSprite(AUTHORS_ICON));

        if (FlowRush.isPlayServicesAvailable()) {
            googlePlayButton.setSprite(FRAssetManager.getSprite(CONTROLLER_ICON));
            signInButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITE));
            showSnapshotsButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITE));
            showAchievementsButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITE));
            signOutButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITE));
        }

        restartButton.setSprite(FRAssetManager.getSprite(RESTART_ICON));
        resumeButton.setSprite(FRAssetManager.getSprite(BACK_BUTTON_ICON));
        menuButton.setSprite(FRAssetManager.getSprite(MENU_ICON));

        pauseButton.setSprite(FRAssetManager.getSprite(PAUSE_ICON));

        pauseBackground.setStyle(FRAssetManager.getLabelStyle(LABEL_STYLE_ALPHAWHITE));

        //Level complete elements
        nextLevelButton.setSprite(FRAssetManager.getSprite(NEXT_ICON));

        wellDonehex.setSprite(FRAssetManager.getSprite(BIG_HEX_LIGHT));

        wellDoneLabel.setStyle(FRAssetManager.getLabelStyle(LABEL_STYLE_DARKBLUE));

        //Pack complete elements
        packCompleteUpperHex.reload(FRAssetManager.getSprite(BIG_HEX_DARK));
        packCompleteLowerHex.setSprite(FRAssetManager.getSprite(BIG_HEX_LIGHT));
        packCompleteMenuButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_DEFAULT));
        packCompleteNextPackButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_DARKBLUE));

        dialogBackground.setStyle(FRAssetManager.getLabelStyle(LABEL_STYLE_DARKBLUESMALL));

        leftButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_BORDERSMALL));
        rightButton.setStyle(FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_WHITESMALL));
    }

    public static void initialize() {

        //----------------------- COMMON

        levelNumberActor = new Label("", FRAssetManager.getLabelStyle(LABEL_STYLE_GREYFONT));
        levelNumberActor.setSize(FRConstants.BUTTON_SIZE, FRConstants.BUTTON_SIZE);
        levelNumberActor.setPosition(SCREEN_WIDTH - levelNumberActor.getWidth(), SCREEN_HEIGHT - levelNumberActor.getHeight());
        levelNumberActor.setAlignment(Align.center);

        soundButton = UiActorCreator.getSmallButtonActor(5);

        hexBackgroundActor = new HexBackgroundActor(FRAssetManager.getSprite(BACKGROUND_ANIMATION));

        backgroundAnimation = new Group();
        for (int type = 1; type < 5; type++) {
            backgroundAnimation.addActor(UiActorCreator.getBackgroundActor(type, false));
            backgroundAnimation.addActor(UiActorCreator.getBackgroundActor(type, true));
        }

        //------------------------------------- MENU

        //Main menu parts
        menuLabel = UiActorCreator.getTextButton(7);
        menuLabel.setText("SUPPORT US");

        innerLayout = new Label("", FRAssetManager.getLabelStyle(LABEL_STYLE_DEFAULT));
        innerLayout.setSize(SCREEN_WIDTH * 0.9f, (((SCREEN_HEIGHT * 0.98f - BUTTON_SIZE)) + (BUTTON_SIZE) / 2) - (BUTTON_SIZE / 2 + SCREEN_HEIGHT * 0.02f));
        innerLayout.setPosition((SCREEN_WIDTH - innerLayout.getWidth()) / 2, BUTTON_SIZE / 2 + SCREEN_HEIGHT * 0.02f);
        innerLayout.setVisible(false);

        messageBackground = new Label(AUTHORS_MSG, FRAssetManager.getLabelStyle(LABEL_STYLE_DEFAULT));
        messageBackground.setVisible(false);
        messageBackground.setAlignment(Align.top);
        messageBackground.setWrap(true);

        labelContainer = new Container<>(messageBackground);
        labelContainer.fill();
        labelContainer.setSize(innerLayout.getWidth() * 0.9f, (innerLayout.getHeight() - BUTTON_SIZE) * 0.95f);
        labelContainer.setPosition((SCREEN_WIDTH - innerLayout.getWidth() * 0.9f) / 2, innerLayout.getY() + BUTTON_SIZE / 2 + (innerLayout.getHeight() - BUTTON_SIZE) * 0.05f / 2);

        closeButton = UiActorCreator.getSmallButtonActor(6);

        triangleBackground = new Image() {
            public void draw(Batch batch, float alpha) {
                batch.draw(FRAssetManager.getBackgroundWhite(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        triangleBackground.setSize(SCREEN_WIDTH * 0.65f, SCREEN_WIDTH * 0.65f * 1.71358024691358f);
        triangleBackground.setPosition(SCREEN_WIDTH - triangleBackground.getWidth(), 0);

        triangleBackgroundTop = new Actor() {
            public void draw(Batch batch, float alpha) {
                batch.draw(FRAssetManager.getBackgroundWhite(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        triangleBackgroundTop.setSize(SCREEN_WIDTH * 0.65f, SCREEN_WIDTH * 0.65f * 1.71358024691358f);
        triangleBackgroundTop.setOrigin(triangleBackgroundTop.getWidth() / 2, triangleBackgroundTop.getHeight() / 2);
        triangleBackgroundTop.setRotation(180);
        triangleBackgroundTop.setPosition(0, SCREEN_HEIGHT - triangleBackgroundTop.getHeight());

        //Main buttons
        playButton = UiActorCreator.getTextButton(1);
        levelsButton = UiActorCreator.getTextButton(2);
        exitButton = UiActorCreator.getTextButton(13);

        packGroup = new Group();
        for (int x = 1; x < 6; x++) {
            TextButton packButton = UiActorCreator.getPackButton(x);
            packGroup.addActor(packButton);
        }
        packGroup.setVisible(false);

        levelNumbersGroup = new Group();
        //levelNumbersGroupArray = new ArrayList<>();
        levelNumbersGroup.setVisible(false);

        for (int x = 1; x < 51; x++) {
            LevelNumberButton levelNumberButton = new LevelNumberButton("" + x, FRAssetManager.getTextButtonStyle(TEXT_BUTTON_STYLE_LIGHTBLUE), FRAssetManager.getSprite(LOCK_ICON), x);
            setLevelNumberPosition(x, levelNumberButton);
            levelNumberButton.setSize(LEVEL_NUMBER_SIZE, LEVEL_NUMBER_SIZE);
            levelNumberButton.setOrigin(Align.center);
            levelNumbersGroup.addActor(levelNumberButton);
        }

        //Day night button
        dayNightButton = UiActorCreator.getSmallButtonActor(15);

        //Social networks
        socialNetworksButton = UiActorCreator.getTextButton(8);

        //Support us
        supportUsSmallButton = UiActorCreator.getSmallButtonActor(13);
        supportUsButton = UiActorCreator.getTextButton(5);
        rateUsButton = UiActorCreator.getTextButton(4);
        feedButton = UiActorCreator.getTextButton(3);
        websiteButton = UiActorCreator.getTextButton(18);
        //Authors
        authorsButton = UiActorCreator.getSmallButtonActor(7);

        removeAdsButton = UiActorCreator.getTextButton(6);

        if (FlowRush.isPlayServicesAvailable()) {
            googlePlayButton = UiActorCreator.getSmallButtonActor(14);
            signInButton = UiActorCreator.getTextButton(14);
            showSnapshotsButton = UiActorCreator.getTextButton(15);
            showAchievementsButton = UiActorCreator.getTextButton(16);
            signOutButton = UiActorCreator.getTextButton(17);
        }

        //---------------------------------- GAME_MAIN

        //Pause menu elements
        pauseGroup = new Group();

        restartButton = UiActorCreator.getSmallButtonActor(3);
        resumeButton = UiActorCreator.getSmallButtonActor(2);
        menuButton = UiActorCreator.getSmallButtonActor(4);

        pauseButton = UiActorCreator.getSmallButtonActor(1);

        quadrantPauseBackground = new Actor() {
            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(FRAssetManager.getQuadrantSprite(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        quadrantPauseBackground.setSize(BUTTON_SIZE * 2 + SCREEN_HEIGHT * 0.1f, BUTTON_SIZE * 2 + SCREEN_HEIGHT * 0.1f);
        quadrantPauseBackground.setPosition(0, 0);

        pauseBackground = new Label("", FRAssetManager.getLabelStyle(LABEL_STYLE_ALPHAWHITE));
        pauseBackground.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        pauseBackground.setVisible(false);
        pauseBackground.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (ScreenManager.getCurrentScreen() == FRConstants.ScreenType.GAME_PAUSE) {
                    ScreenManager.setGameMainScreen();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        //Level complete elements
        nextLevelButton = UiActorCreator.getSmallButtonActor(12);
        wellDonehex = UiActorCreator.getSmallButtonActor(11);
        wellDoneLabel = new Label("WELL DONE!", FRAssetManager.getLabelStyle(LABEL_STYLE_DARKBLUE));
        wellDoneLabel.setSize(SCREEN_WIDTH * 0.6f, BUTTON_SIZE * 0.7f);
        wellDoneLabel.setPosition((SCREEN_WIDTH - wellDoneLabel.getWidth()) / 2, SCREEN_HEIGHT - BUTTON_SIZE * 0.85f);
        wellDoneLabel.setAlignment(Align.center);
        wellDoneLabel.setVisible(false);

        //Pack complete elements
        packCompleteUpperHex = new PackCompleteTopHex(LevelController.getPackName(FlowRush.getSave().getCurrentPack()));
        packCompleteLowerHex = new PackCompleteLowerHex();
        packCompleteMenuButton = UiActorCreator.getTextButton(9);
        packCompleteNextPackButton = UiActorCreator.getTextButton(12);

        dialogBackground = new Label(QUESTION_1, FRAssetManager.getLabelStyle(LABEL_STYLE_DARKBLUESMALL));
        dialogBackground.setSize(SCREEN_WIDTH, BUTTON_SIZE * 1.45f);
        dialogBackground.setPosition(0, 0);
        dialogBackground.setAlignment(Align.top);

        leftButton = UiActorCreator.getTextButton(11);
        rightButton = UiActorCreator.getTextButton(10);
    }

    public static void getLevelNumbersGroup(int pack) {
        for(int x = 0; x < levelNumbersGroup.getChildren().size; x++){
            LevelNumberButton levelNumberButton = (LevelNumberButton) levelNumbersGroup.getChildren().get(x);
            levelNumberButton.setIsAvailable(levelNumberButton.getLevel() <= FlowRush.getSave().getLevelsProgress(pack - 1));
            levelNumberButton.setPack(pack);
        }
    }

    private static void setLevelNumberPosition(int number, Actor actor) {
        int b = (int) Math.ceil(number / 5.0);
        int a = number - 1 - 5 * (b - 1);

        float xPos = LEVEL_NUMBER_GROUP_MARGIN_LEFT + LEVEL_NUMBER_PADDING * a + LEVEL_NUMBER_SIZE * a;
        float yPos = LEVEL_NUMBER_GROUP_TOP_MARGIN - LEVEL_NUMBER_PADDING * b - LEVEL_NUMBER_SIZE * b;

        actor.setPosition(xPos, yPos);
    }

    //-------------------------------------------------------- COMMON ELEMENTS

    public static Label getLevelNumberActor() {
        return levelNumberActor;
    }

    public static Label getPauseBackground() {
        return pauseBackground;
    }

    public static SmallButtonActor getSoundButton() {
        return soundButton;
    }

    public static HexBackgroundActor getHexBackgroundActor() {
        return hexBackgroundActor;
    }

    public static Group getBackgroundAnimation() {
        return backgroundAnimation;
    }


    //-----------------------------------------------------------  MAIN MENU ELEMENTS


    public static TextButton getRemoveAdsButton() {
        return removeAdsButton;
    }

    public static SmallButtonActor getDayNightButton() {
        return dayNightButton;
    }

    public static TextButton getSocialNetworksButton() {
        return socialNetworksButton;
    }

    public static TextButton getShowAchievementsButton() {
        return showAchievementsButton;
    }

    public static TextButton getShowSnapshotsButton() {
        return showSnapshotsButton;
    }

    public static TextButton getSignOutButton() {
        return signOutButton;
    }

    public static TextButton getSignInButton() {
        return signInButton;
    }

    public static TextButton getLevelsButton() {
        return levelsButton;
    }

    public static TextButton getExitButton() {
        return exitButton;
    }

    public static TextButton getPlayButton() {
        return playButton;
    }

    public static Actor getTriangleBackgroundTop() {
        return triangleBackgroundTop;
    }

    public static Actor getTriangleBackground() {
        return triangleBackground;
    }

    public static SmallButtonActor getCloseButton() {
        return closeButton;
    }

    public static Container<Label> getLabelContainer() {
        return labelContainer;
    }

    public static Label getMessageBackground() {
        return messageBackground;
    }

    public static Label getInnerLayout() {
        return innerLayout;
    }

    public static TextButton getMenuLabel() {
        return menuLabel;
    }

    public static SmallButtonActor getAuthorsButton() {
        return authorsButton;
    }

    public static TextButton getFeedButton() {
        return feedButton;
    }

    public static TextButton getRateUsButton() {
        return rateUsButton;
    }

    public static TextButton getSupportUsButton() {
        return supportUsButton;
    }

    public static SmallButtonActor getSupportUsSmallButton() {
        return supportUsSmallButton;
    }

    public static SmallButtonActor getGooglePlayButton() {
        return googlePlayButton;
    }

    public static Group getPackGroup() {
        return packGroup;
    }

    public static Group getLevelNumbersGroup() {
        return levelNumbersGroup;
    }

    public static TextButton getWebsiteButton() {
        return websiteButton;
    }

    //---------------------------------------------- Game screen elements

    public static Group getPauseGroup() {
        return pauseGroup;
    }

    public static SmallButtonActor getRestartButton() {
        return restartButton;
    }

    public static SmallButtonActor getMenuButton() {
        return menuButton;
    }

    public static SmallButtonActor getResumeButton() {
        return resumeButton;
    }

    public static SmallButtonActor getPauseButton() {
        return pauseButton;
    }

    public static Actor getQuadrantPauseBackground() {
        return quadrantPauseBackground;
    }

    public static Label getWellDoneLabel() {
        return wellDoneLabel;
    }

    public static SmallButtonActor getNextLevelButton() {
        return nextLevelButton;
    }

    public static SmallButtonActor getWellDonehex() {
        return wellDonehex;
    }

    public static PackCompleteLowerHex getPackCompleteLowerHex() {
        return packCompleteLowerHex;
    }

    public static TextButton getPackCompleteMenuButton() {
        return packCompleteMenuButton;
    }

    public static TextButton getPackCompleteNextPackButton() {
        return packCompleteNextPackButton;
    }

    public static PackCompleteTopHex getPackCompleteUpperHex() {
        return packCompleteUpperHex;
    }

    public static TextButton getLeftButton() {
        return leftButton;
    }

    public static TextButton getRightButton() {
        return rightButton;
    }

    public static Label getDialogBackground() {
        return dialogBackground;
    }
}
