package com.blackhornetworkshop.flowrush.ui;

//Created by TScissors.

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.ConstantBase;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.initialization.LevelLoader;
import com.blackhornetworkshop.flowrush.initialization.UiActorCreator;
import com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener;
import com.blackhornetworkshop.flowrush.listeners.LeftButtonListener;
import com.blackhornetworkshop.flowrush.listeners.RightButtonListener;
import com.blackhornetworkshop.flowrush.screens.GameScreen;
import com.blackhornetworkshop.flowrush.screens.MenuScreen;

public class UIPool {

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
    private static Actor whiteBackActorTop;
    private static Actor whiteBackActor;

    //Main buttons
    private static TextButton playButton;
    private static TextButton exitButton;
    private static TextButton lvlButton;
    private static Group packGroup;
    private static Group levelGroup;
    /**
     * private TextButton removeAds;
     */

    //--------------------- GAME

    // Pause menu elements
    private static SmallButtonActor pauseButton;
    private static SmallButtonActor restartButton;
    private static SmallButtonActor mainMenuButton;
    private static SmallButtonActor backButton;
    private static Actor quadrant;

    // Level complete elements
    private static Label wellDoneLabel;
    private static SmallButtonActor nextLevelButton;
    private static SmallButtonActor wellDonehex;

    // Pack complete elements
    private static PackBackActor packCompleteLowerHex;
    private static PackCompleteActor packCompleteUpperHex;
    private static TextButton packCompleteMenuButton;
    private static TextButton packCompleteNextPackButton;
    private static TextButton leftButton, rightButton;
    private static Label dialogBackground;


    public static void initialize() {

        //------------------------------------- MENU

        //Main menu parts
        menuLabel = UiActorCreator.getTextButton(7);
        menuLabel.setText("SUPPORT US");

        innerLayout = new Label("", FlowRush.getInstance().skin, "default");
        innerLayout.setSize(Gdx.graphics.getWidth() * 0.9f, (((Gdx.graphics.getHeight() * 0.98f - ConstantBase.C_BUTTON_SIZE)) + (ConstantBase.C_BUTTON_SIZE) / 2) - (ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f)); // размеры up и down иннерскрин? высчитываем через высоту textButton любого выше высчитываем через высоту кнопки circle button back
        innerLayout.setPosition((Gdx.graphics.getWidth() - innerLayout.getWidth()) / 2, ConstantBase.C_BUTTON_SIZE / 2 + Gdx.graphics.getHeight() * 0.02f);
        innerLayout.setVisible(false);

        messageBackground = new Label("development\nTIMUR SCISSORS\n\ndesign\nSONYA KOVALSKI\n\nmusic\nERIC HOPTON", FlowRush.getInstance().skin, "default");
        messageBackground.setVisible(false);
        messageBackground.setAlignment(Align.top);
        messageBackground.setWrap(true);

        labelContainer = new Container<Label>(messageBackground); //Контейнер нужен для того чтобы сделать перенос строки
        labelContainer.fill();
        labelContainer.setSize(innerLayout.getWidth() * 0.9f, (innerLayout.getHeight() - ConstantBase.C_BUTTON_SIZE) * 0.95f);
        labelContainer.setPosition((Gdx.graphics.getWidth() - innerLayout.getWidth() * 0.9f) / 2, innerLayout.getY() + ConstantBase.C_BUTTON_SIZE / 2 + (innerLayout.getHeight() - ConstantBase.C_BUTTON_SIZE) * 0.05f / 2);

        closeButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(6);

        final Sprite backgroundWhite = FlowRush.getInstance().atlas.createSprite("back_white");
        whiteBackActor = new Image() {
            public void draw(Batch batch, float alpha) {
                batch.draw(backgroundWhite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        whiteBackActor.setSize(Gdx.graphics.getWidth() * 0.65f, Gdx.graphics.getWidth() * 0.65f * 1.71358024691358f);
        whiteBackActor.setPosition(Gdx.graphics.getWidth() - whiteBackActor.getWidth(), 0);

        whiteBackActorTop = new Actor() {
            public void draw(Batch batch, float alpha) {
                batch.draw(backgroundWhite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        whiteBackActorTop.setSize(Gdx.graphics.getWidth() * 0.65f, Gdx.graphics.getWidth() * 0.65f * 1.71358024691358f);
        whiteBackActorTop.setOrigin(whiteBackActorTop.getWidth() / 2, whiteBackActorTop.getHeight() / 2);
        whiteBackActorTop.setRotation(180);
        whiteBackActorTop.setPosition(0, Gdx.graphics.getHeight() - whiteBackActorTop.getHeight());

        //Main buttons
        playButton = UiActorCreator.getTextButton(1);
        playButton.addListener(new ButtonScaleListener());
        lvlButton = UiActorCreator.getTextButton(2);
        lvlButton.addListener(new ButtonScaleListener());
        exitButton = UiActorCreator.getTextButton(13);
        exitButton.setVisible(false);

        packGroup = new Group();
        for(int x = 1; x<6; x++){
            final int p = x;
            TextButton packButton = UiActorCreator.getPackTextButton(p);
            packButton.addListener(new ButtonScaleListener());
            if(LevelLoader.getInstance().getLevelPack(p-1).available) {
                packButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        MenuScreen.getInstance().selectLevelScreen(p);
                    }
                });
            }
            packGroup.addActor(packButton);
        }
        packGroup.setVisible(false);

        levelGroup = new Group(); //класс пустышка, так оптимальней чтобы сразу он там был в случае нескольких кликов по пакам оправдано, сразу есть что удалять в слушателе ниже
        levelGroup.setVisible(false);

        //Social networks
        socialNetworkBackground = UiActorCreator.getTextButton(8);
        twitterButton = UiActorCreator.getSmallButtonActor(9);
        facebookButton = UiActorCreator.getSmallButtonActor(8);
        vkButton = UiActorCreator.getSmallButtonActor(10);

        //Support us
        supportUsSmallButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(13);
        supportUsButton = UiActorCreator.getTextButton(5);
        supportUsButton.addListener(new ButtonScaleListener());
        rateUsButton = UiActorCreator.getTextButton(4);
        rateUsButton.addListener(new ButtonScaleListener());
        feedButton = UiActorCreator.getTextButton(3);
        feedButton.addListener(new ButtonScaleListener());

        //Authors
        authorsButton = UiActorCreator.getSmallButtonActor(7);

        //!!!!!!!!!!! НЕ УДАЛИТЬ СЛУЧАЙНО
        /**removeAds = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getTextButton(6, game);
         removeAds.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(removeAds, game));*/

        if (FlowRush.isPlayServicesAvailable) {
            googlePlayButton = UiActorCreator.getSmallButtonActor(14);
            signInButton = UiActorCreator.getTextButton(14);
            signInButton.addListener(new ButtonScaleListener());
            showSnapshotsButton = UiActorCreator.getTextButton(15);
            showSnapshotsButton.addListener(new ButtonScaleListener());
            showAchievementsButton = UiActorCreator.getTextButton(16);
            showAchievementsButton.addListener(new ButtonScaleListener());
            signOutButton = UiActorCreator.getTextButton(17);
            signOutButton.addListener(new ButtonScaleListener());
        }

        //---------------------------------- GAME

        //Pause menu elements
        restartButton = UiActorCreator.getSmallButtonActor(3);
        mainMenuButton = UiActorCreator.getSmallButtonActor(4);
        pauseButton = UiActorCreator.getSmallButtonActor(1);
        backButton = UiActorCreator.getSmallButtonActor(2);
        quadrant = new Actor() {
            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(FlowRush.getInstance().qCircle, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        quadrant.setSize(ConstantBase.C_BUTTON_SIZE*2 + Gdx.graphics.getHeight() * 0.1f, ConstantBase.C_BUTTON_SIZE*2 + Gdx.graphics.getHeight() * 0.1f);
        quadrant.setPosition(0, 0);


        //Level complete elements
        nextLevelButton = UiActorCreator.getSmallButtonActor(12);
        wellDonehex = UiActorCreator.getSmallButtonActor(11);
        wellDoneLabel = new Label("WELL DONE!", FlowRush.getInstance().skin, "darkblue");
        wellDoneLabel.setSize(Gdx.graphics.getWidth() * 0.6f, ConstantBase.C_BUTTON_SIZE * 0.7f);
        wellDoneLabel.setPosition((Gdx.graphics.getWidth() - wellDoneLabel.getWidth()) / 2, Gdx.graphics.getHeight() - ConstantBase.C_BUTTON_SIZE * 0.85f);
        wellDoneLabel.setAlignment(Align.center);
        wellDoneLabel.setVisible(false);

        //Pack complete elements
        packCompleteUpperHex = new PackCompleteActor(FlowRush.getInstance().atlas, FlowRush.getInstance().skin.getFont("fontMid"), FlowRush.getInstance().save.getPackName());
        packCompleteLowerHex = new PackBackActor(FlowRush.getInstance().atlas);
        packCompleteMenuButton = UiActorCreator.getTextButton(9);
        packCompleteNextPackButton = UiActorCreator.getTextButton(12);
        packCompleteNextPackButton.setName("");

        dialogBackground = new Label("ENJOYING  FLOW RUSH?", FlowRush.getInstance().skin, "darkbluesmall");
        dialogBackground.setSize(Gdx.graphics.getWidth(), ConstantBase.C_BUTTON_SIZE * 1.45f);
        dialogBackground.setPosition(0, 0);
        dialogBackground.setAlignment(Align.top);

        leftButton = UiActorCreator.getTextButton(10);
        leftButton.setStyle(FlowRush.getInstance().skin.get("bordersmall", TextButton.TextButtonStyle.class));
        leftButton.setText("NOT SURE");
        leftButton.addListener(new ButtonScaleListener());
        LeftButtonListener leftButtonListener = new LeftButtonListener();
        leftButton.addListener(leftButtonListener);

        rightButton = UiActorCreator.getTextButton(10);
        rightButton.setStyle(FlowRush.getInstance().skin.get("whitesmall", TextButton.TextButtonStyle.class));
        rightButton.setText("YES!");
        rightButton.setX((Gdx.graphics.getWidth() - rightButton.getWidth() * 2) / 3 * 2 + rightButton.getWidth());
        rightButton.addListener(new ButtonScaleListener());
        rightButton.addListener(new RightButtonListener(leftButtonListener));

    }


    //Main menu elements

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

    public static TextButton getLvlButton() {
        return lvlButton;
    }

    public static TextButton getExitButton() {
        return exitButton;
    }

    public static TextButton getPlayButton() {
        return playButton;
    }

    public static Actor getWhiteBackActorTop() {return whiteBackActorTop;}

    public static Actor getWhiteBackActor() {
        return whiteBackActor;
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

    public static Group getPackGroup() { return packGroup; }

    public static Group getLevelGroup() { return levelGroup; }

    //Game screen elements

    public static SmallButtonActor getPauseButton() { return pauseButton; }

    public static SmallButtonActor getRestartButton() { return restartButton; }

    public static SmallButtonActor getMainMenuButton() { return mainMenuButton; }

    public static SmallButtonActor getBackButton() { return backButton; }

    public static Actor getQuadrant() { return quadrant; }

    public static Label getWellDoneLabel() { return wellDoneLabel; }

    public static SmallButtonActor getNextLevelButton() { return nextLevelButton; }

    public static SmallButtonActor getWellDonehex() { return wellDonehex; }

    public static PackBackActor getPackCompleteLowerHex() { return packCompleteLowerHex; }

    public static TextButton getPackCompleteMenuButton() { return packCompleteMenuButton; }

    public static TextButton getPackCompleteNextPackButton() { return packCompleteNextPackButton; }

    public static PackCompleteActor getPackCompleteUpperHex() { return packCompleteUpperHex; }

    public static TextButton getLeftButton() { return leftButton; }

    public static TextButton getRightButton() { return rightButton; }

    public static Label getDialogBackground() { return dialogBackground; }
}
