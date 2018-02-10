package com.blackhornetworkshop.flowrush.model.ui;

//Created by TScissors.

import com.badlogic.gdx.Gdx;
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
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.controller.LevelController;
import com.blackhornetworkshop.flowrush.model.UiActorCreator;

import java.util.ArrayList;

import static com.blackhornetworkshop.flowrush.model.FRConstants.LEVEL_NUMBER_GROUP_MARGIN_LEFT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.LEVEL_NUMBER_GROUP_TOP_MARGIN;
import static com.blackhornetworkshop.flowrush.model.FRConstants.LEVEL_NUMBER_PADDING;
import static com.blackhornetworkshop.flowrush.model.FRConstants.LEVEL_NUMBER_SIZE;

public class UIPool {

    //----------------------- COMMON

    private static Group backgroundAnimation;
    private static HexBackgroundActor hexBackgroundActor;
    private static SmallButtonActor soundButton;
    private static Label pauseBackground, levelNumberActor;

    //------------------------ MENU

    //Social networks
    private static TextButton socialNetworkBackground;
    private static SmallButtonActor twitterButton;
    private static SmallButtonActor facebookButton;
    private static SmallButtonActor vkButton;

    //Google play
    private static SmallButtonActor googlePlayButton;
    private static TextButton showAchievementsButton;
    private static TextButton showSnapshotsButton;
    private static TextButton signOutButton;
    private static TextButton signInButton;

    //Support us
    private static SmallButtonActor supportUsSmallButton;
    private static TextButton supportUsButton;
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
    private static ArrayList<LevelNumberButton> levelNumbersGroupArray;
    private static SmallButtonActor dayNightButton;

    //Main buttons
    private static TextButton playButton;
    private static TextButton exitButton;
    private static TextButton levelsButton;
    private static Group packGroup;

    /**
     * private TextButton removeAds;
     */

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


    public static void initialize() {

        //----------------------- COMMON

        levelNumberActor = new Label("", FRAssetManager.getSkin(), "greyfont");
        levelNumberActor.setSize(FRConstants.BUTTON_SIZE, FRConstants.BUTTON_SIZE);
        levelNumberActor.setPosition(Gdx.graphics.getWidth() - levelNumberActor.getWidth(), Gdx.graphics.getHeight() - levelNumberActor.getHeight());
        levelNumberActor.setAlignment(Align.center);

        soundButton = UiActorCreator.getSmallButtonActor(5);

        hexBackgroundActor = new HexBackgroundActor(FRAssetManager.getAtlas().createSprite("animbackhex"));

        backgroundAnimation = new Group();
        for (int type = 1; type < 5; type++) {
            backgroundAnimation.addActor(UiActorCreator.getBackgroundActor(type, false));
            backgroundAnimation.addActor(UiActorCreator.getBackgroundActor(type, true));
        }

        //------------------------------------- MENU

        //Main menu parts
        menuLabel = UiActorCreator.getTextButton(7);
        menuLabel.setText("SUPPORT US");

        innerLayout = new Label("", FRAssetManager.getSkin(), "default");
        innerLayout.setSize(Gdx.graphics.getWidth() * 0.9f, (((Gdx.graphics.getHeight() * 0.98f - FRConstants.BUTTON_SIZE)) + (FRConstants.BUTTON_SIZE) / 2) - (FRConstants.BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f)); // размеры PACK_GROUP_TOP_MARGIN и PACK_GROUP_BOTTOM_MARGIN иннерскрин? высчитываем через высоту textButton любого выше высчитываем через высоту кнопки circle button back
        innerLayout.setPosition((Gdx.graphics.getWidth() - innerLayout.getWidth()) / 2, FRConstants.BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f);
        innerLayout.setVisible(false);

        messageBackground = new Label("development\nTIMUR SCISSORS\n\ndesign\nSONYA KOVALSKI\n\nmusic\nERIC HOPTON", FRAssetManager.getSkin(), "default");
        messageBackground.setVisible(false);
        messageBackground.setAlignment(Align.top);
        messageBackground.setWrap(true);

        labelContainer = new Container<>(messageBackground); //Контейнер нужен для того чтобы сделать перенос строки
        labelContainer.fill();
        labelContainer.setSize(innerLayout.getWidth() * 0.9f, (innerLayout.getHeight() - FRConstants.BUTTON_SIZE) * 0.95f);
        labelContainer.setPosition((Gdx.graphics.getWidth() - innerLayout.getWidth() * 0.9f) / 2, innerLayout.getY() + FRConstants.BUTTON_SIZE / 2 + (innerLayout.getHeight() - FRConstants.BUTTON_SIZE) * 0.05f / 2);

        closeButton = UiActorCreator.getSmallButtonActor(6);

        triangleBackground = new Image() {
            public void draw(Batch batch, float alpha) {
                batch.draw(FRAssetManager.getBackgroundWhite(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        triangleBackground.setSize(Gdx.graphics.getWidth() * 0.65f, Gdx.graphics.getWidth() * 0.65f * 1.71358024691358f);
        triangleBackground.setPosition(Gdx.graphics.getWidth() - triangleBackground.getWidth(), 0);

        triangleBackgroundTop = new Actor() {
            public void draw(Batch batch, float alpha) {
                batch.draw(FRAssetManager.getBackgroundWhite(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        triangleBackgroundTop.setSize(Gdx.graphics.getWidth() * 0.65f, Gdx.graphics.getWidth() * 0.65f * 1.71358024691358f);
        triangleBackgroundTop.setOrigin(triangleBackgroundTop.getWidth() / 2, triangleBackgroundTop.getHeight() / 2);
        triangleBackgroundTop.setRotation(180);
        triangleBackgroundTop.setPosition(0, Gdx.graphics.getHeight() - triangleBackgroundTop.getHeight());

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
        levelNumbersGroupArray = new ArrayList<>();
        levelNumbersGroup.setVisible(false);

        for (int x = 1; x < 51; x++) {
            LevelNumberButton levelNumberButton = new LevelNumberButton("" + x, FRAssetManager.getSkin(), "lightblue", x);
            setLevelNumberPosition(x, levelNumberButton);
            levelNumberButton.setSize(LEVEL_NUMBER_SIZE, LEVEL_NUMBER_SIZE);
            levelNumberButton.setOrigin(Align.center);
            levelNumbersGroup.addActor(levelNumberButton);
            levelNumbersGroupArray.add(levelNumberButton);
        }

        //Day night button
        dayNightButton = UiActorCreator.getSmallButtonActor(15);

        //Social networks
        socialNetworkBackground = UiActorCreator.getTextButton(8);
        twitterButton = UiActorCreator.getSmallButtonActor(9);
        facebookButton = UiActorCreator.getSmallButtonActor(8);
        vkButton = UiActorCreator.getSmallButtonActor(10);

        //Support us
        supportUsSmallButton = UiActorCreator.getSmallButtonActor(13);
        supportUsButton = UiActorCreator.getTextButton(5);
        rateUsButton = UiActorCreator.getTextButton(4);
        feedButton = UiActorCreator.getTextButton(3);

        //Authors
        authorsButton = UiActorCreator.getSmallButtonActor(7);

        //!!!!!!!!!!! НЕ УДАЛИТЬ СЛУЧАЙНО
        /**removeAds = com.blackhornetworkshop.flowrush.model.UiActorCreator.getTextButton(6, game);
         removeAds.addListener(new com.blackhornetworkshop.flowrush.model.listeners.ButtonScaleListener(removeAds, game));*/

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
        quadrantPauseBackground.setSize(FRConstants.BUTTON_SIZE * 2 + Gdx.graphics.getHeight() * 0.1f, FRConstants.BUTTON_SIZE * 2 + Gdx.graphics.getHeight() * 0.1f);
        quadrantPauseBackground.setPosition(0, 0);

        pauseBackground = new Label("", FRAssetManager.getSkin(), "alphawhite");
        pauseBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        wellDoneLabel = new Label("WELL DONE!", FRAssetManager.getSkin(), "darkblue");
        wellDoneLabel.setSize(Gdx.graphics.getWidth() * 0.6f, FRConstants.BUTTON_SIZE * 0.7f);
        wellDoneLabel.setPosition((Gdx.graphics.getWidth() - wellDoneLabel.getWidth()) / 2, Gdx.graphics.getHeight() - FRConstants.BUTTON_SIZE * 0.85f);
        wellDoneLabel.setAlignment(Align.center);
        wellDoneLabel.setVisible(false);

        //Pack complete elements
        packCompleteUpperHex = new PackCompleteTopHex(FRAssetManager.getSkin().getFont("fontMid"), LevelController.getPackName(FlowRush.getSave().getCurrentPack()));
        packCompleteLowerHex = new PackCompleteLowerHex(FRAssetManager.getAtlas());
        packCompleteMenuButton = UiActorCreator.getTextButton(9);
        packCompleteNextPackButton = UiActorCreator.getTextButton(12);
        packCompleteNextPackButton.setName("");

        dialogBackground = new Label("ENJOYING  FLOW RUSH?", FRAssetManager.getSkin(), "darkbluesmall");
        dialogBackground.setSize(Gdx.graphics.getWidth(), FRConstants.BUTTON_SIZE * 1.45f);
        dialogBackground.setPosition(0, 0);
        dialogBackground.setAlignment(Align.top);

        leftButton = UiActorCreator.getTextButton(11);
        rightButton = UiActorCreator.getTextButton(10);
    }

    public static void getLevelNumbersGroup(int pack) {
        for (LevelNumberButton levelNumberButton : levelNumbersGroupArray) {
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


    public static SmallButtonActor getDayNightButton() {
        return dayNightButton;
    }

    public static SmallButtonActor getFacebookButton() {
        return facebookButton;
    }

    public static SmallButtonActor getTwitterButton() {
        return twitterButton;
    }

    public static SmallButtonActor getVkButton() {
        return vkButton;
    }

    public static TextButton getSocialNetworkBackground() {
        return socialNetworkBackground;
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
