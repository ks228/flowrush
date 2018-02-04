package com.blackhornetworkshop.flowrush;

//Created by TScissors.

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.initialization.UiActorCreator;
import com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener;
import com.blackhornetworkshop.flowrush.ui.SmallButtonActor;

public class UIPool {
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

    /**
     * private TextButton removeAds;
     */

    public static void initialize() {
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

        whiteBackActor = new Image() {
            public void draw(Batch batch, float alpha) {
                batch.draw(FlowRush.getInstance().atlas.createSprite("back_white"), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        whiteBackActor.setSize(Gdx.graphics.getWidth() * 0.65f, Gdx.graphics.getWidth() * 0.65f * 1.71358024691358f);
        whiteBackActor.setPosition(Gdx.graphics.getWidth() - whiteBackActor.getWidth(), 0);

        whiteBackActorTop = new Actor() {
            public void draw(Batch batch, float alpha) {
                batch.draw(FlowRush.getInstance().atlas.createSprite("back_white"), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        whiteBackActorTop.setSize(Gdx.graphics.getWidth() * 0.65f, Gdx.graphics.getWidth() * 0.65f * 1.71358024691358f);
        whiteBackActorTop.setOrigin(whiteBackActorTop.getWidth() / 2, whiteBackActorTop.getHeight() / 2);
        whiteBackActorTop.setRotation(180);
        whiteBackActorTop.setPosition(0, Gdx.graphics.getHeight() - whiteBackActorTop.getHeight());

        //Main buttons
        playButton = UiActorCreator.getTextButton(1);
        //playButton.addListener(new ButtonScaleListener(playButton)); // DELETED IN 1.04 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        lvlButton = UiActorCreator.getTextButton(2);
        lvlButton.addListener(new ButtonScaleListener(lvlButton));
        exitButton = UiActorCreator.getTextButton(13);
        exitButton.setVisible(false);

        //Social networks
        socialNetworkBackground = UiActorCreator.getTextButton(8);
        twitterButton = UiActorCreator.getSmallButtonActor(9);
        facebookButton = UiActorCreator.getSmallButtonActor(8);
        vkButton = UiActorCreator.getSmallButtonActor(10);

        //Support us
        supportUsSmallButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(13);
        supportUsButton = UiActorCreator.getTextButton(5);
        supportUsButton.addListener(new ButtonScaleListener(supportUsButton));
        rateUsButton = UiActorCreator.getTextButton(4);
        rateUsButton.addListener(new ButtonScaleListener(rateUsButton));
        feedButton = UiActorCreator.getTextButton(3);
        feedButton.addListener(new ButtonScaleListener(feedButton));

        //Authors
        authorsButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(7);

        //!!!!!!!!!!! НЕ УДАЛИТЬ СЛУЧАЙНО
        /**removeAds = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getTextButton(6, game);
         removeAds.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(removeAds, game));*/

        if (FlowRush.isPlayServicesAvailable) {
            googlePlayButton = UiActorCreator.getSmallButtonActor(14);
            signInButton = UiActorCreator.getTextButton(14);
            signInButton.addListener(new ButtonScaleListener(signInButton));
            showSnapshotsButton = UiActorCreator.getTextButton(15);
            showSnapshotsButton.addListener(new ButtonScaleListener(showSnapshotsButton));
            showAchievementsButton = UiActorCreator.getTextButton(16);
            showAchievementsButton.addListener(new ButtonScaleListener(showAchievementsButton));
            signOutButton = UiActorCreator.getTextButton(17);
            signOutButton.addListener(new ButtonScaleListener(signOutButton));
        }
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

    public static TextButton getLvlButton() {
        return lvlButton;
    }

    public static TextButton getExitButton() {
        return exitButton;
    }

    public static TextButton getPlayButton() {
        return playButton;
    }

    public static Actor getWhiteBackActorTop() {

        return whiteBackActorTop;
    }

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
}
