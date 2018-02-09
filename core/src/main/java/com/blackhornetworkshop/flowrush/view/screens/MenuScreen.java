package com.blackhornetworkshop.flowrush.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.controller.LevelController;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;

//Created by TScissors.

public class MenuScreen implements Screen, FRScreen {

    private static MenuScreen instance;
    private Stage hudStage;
    private boolean isActive;

    public static MenuScreen getInstance(){
        if(instance == null) instance = new MenuScreen();
        return instance;
    }

    private MenuScreen() {}

    @Override
    public void show() {
        FlowRush.logDebug("Main menu screen show() method called");

        isActive = true;

        Gdx.gl.glClearColor(0.26f, 0.64f, 0.87f, 1);

        hudStage = FlowRush.getInstance().getHudStage();

        LevelController.setCurrentLevel(FlowRush.getSave().getCurrentPack(), FlowRush.getSave().getCurrentLvl());

        UIPool.getSoundButton().setPosition(Gdx.graphics.getHeight()*0.02f, Gdx.graphics.getHeight()*0.02f);
        UIPool.getSoundButton().setVisible(true);

        hudStage.addActor(UIPool.getTriangleBackground());
        hudStage.addActor(UIPool.getTriangleBackgroundTop());
        hudStage.addActor(UIPool.getBackgroundAnimation());
        hudStage.addActor(UIPool.getPlayButton());
        hudStage.addActor(UIPool.getLevelsButton());
        hudStage.addActor(UIPool.getExitButton());
        hudStage.addActor(UIPool.getAuthorsButton());
        hudStage.addActor(UIPool.getSoundButton());
        hudStage.addActor(UIPool.getSupportUsSmallButton());
        hudStage.addActor(UIPool.getInnerLayout());
        hudStage.addActor(UIPool.getLabelContainer());
        hudStage.addActor(UIPool.getPackGroup());
        hudStage.addActor(UIPool.getSupportUsButton());
        hudStage.addActor(UIPool.getRateUsButton());
        hudStage.addActor(UIPool.getFeedButton());
        /**hudStage.addActor(removeAds);*/
        hudStage.addActor(UIPool.getSocialNetworkBackground());
        hudStage.addActor(UIPool.getTwitterButton());
        hudStage.addActor(UIPool.getFacebookButton());
        hudStage.addActor(UIPool.getVkButton());
        hudStage.addActor(UIPool.getLevelNumbersGroup());
        hudStage.addActor(UIPool.getCloseButton());
        hudStage.addActor(UIPool.getMenuLabel());

        if(FlowRush.isPlayServicesAvailable()) {
            hudStage.addActor(UIPool.getGooglePlayButton());
            hudStage.addActor(UIPool.getSignInButton());
            hudStage.addActor(UIPool.getSignOutButton());
            hudStage.addActor(UIPool.getShowSnapshotsButton());
            hudStage.addActor(UIPool.getShowAchievementsButton());
        }

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(0, FlowRush.getOneTouchProcessor());
        inputMultiplexer.addProcessor(1, FlowRush.getInstance().getHudStage());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    @Override
    public void hide() {
        FlowRush.logDebug("Main menu screen hide() method called");
        hudStage.clear();
        isActive = false;
    }

    public void setMainMenuScreen(){
        UIPool.getMenuLabel().setVisible(false);
        UIPool.getLevelNumbersGroup().setVisible(false);
        UIPool.getInnerLayout().setVisible(false);
        UIPool.getCloseButton().setVisible(false);
        UIPool.getMessageBackground().setVisible(false);
        UIPool.getPackGroup().setVisible(false);
        UIPool.getSupportUsButton().setVisible(false);
        UIPool.getRateUsButton().setVisible(false);
        UIPool.getFeedButton().setVisible(false);
        UIPool.getSocialNetworkBackground().setVisible(false);
        /**removeAds.setVisible(false);*/
        UIPool.getTwitterButton().setVisible(false);
        UIPool.getFacebookButton().setVisible(false);
        UIPool.getVkButton().setVisible(false);

        if(FlowRush.isPlayServicesAvailable()) {
            UIPool.getGooglePlayButton().setVisible(true);
            UIPool.getSignInButton().setVisible(false);
            UIPool.getShowSnapshotsButton().setVisible(false);
            UIPool.getShowAchievementsButton().setVisible(false);
            UIPool.getSignOutButton().setVisible(false);
        }

        UIPool.getPlayButton().setVisible(true);
        UIPool.getLevelsButton().setVisible(true);
        UIPool.getSoundButton().setVisible(true);
        UIPool.getAuthorsButton().setVisible(true);
        UIPool.getSupportUsSmallButton().setVisible(true);
    }

    public void setLevelChoiceScreen(int pack){
        setOnVisibleForInnerScreen();
        UIPool.getMenuLabel().setText("SELECT LEVEL");
        UIPool.getLevelNumbersGroup(pack);
        UIPool.getLevelNumbersGroup().setVisible(true);
    }

    public void setPackChoiceScreen(){
        setOnVisibleForInnerScreen();
        UIPool.getMenuLabel().setText("SELECT PACK");
        UIPool.getPackGroup().setVisible(true);
    }

    public void setSupportUsScreen(){
        setOnVisibleForInnerScreen();
        UIPool.getSupportUsButton().setVisible(false);
        UIPool.getRateUsButton().setVisible(false);
        UIPool.getFeedButton().setVisible(false);

        /**messageBack.setText("\nPlease support our indie development team. Remove ads or share our game with your friends .\n\nYou help us to grow.\nThank you!");*/
        UIPool.getMessageBackground().setText("Please support our indie development team. Share our game with your friends.\n\nYou help us to grow.\nThank you!");
        UIPool.getMenuLabel().setText("SUPPORT US");

        UIPool.getMessageBackground().setVisible(true);
        UIPool.getSocialNetworkBackground().setVisible(true);
        /**removeAds.setVisible(true);*/
        UIPool.getTwitterButton().setVisible(true);
        UIPool.getFacebookButton().setVisible(true);
        UIPool.getVkButton().setVisible(true);
    }

    public void setAuthorsScreen(){
        UIPool.getMessageBackground().setText("development\nTIMUR SCISSORS\n\ndesign\nSONYA KOVALSKI\n\nmusic\nERIC HOPTON");
        UIPool.getMenuLabel().setText("AUTHORS");
        setOnVisibleForInnerScreen();

        UIPool.getMessageBackground().setVisible(true);
        UIPool.getSupportUsButton().setVisible(true);
        UIPool.getRateUsButton().setVisible(true);
        UIPool.getFeedButton().setVisible(true);
    }

    public void setSignInScreen(){
        UIPool.getMessageBackground().setText("By signing in your game progress will be saved online " +
                "with Google Play. Your Google Play Games Achievements will be associated with your" +
                "Google+ indentity. These will be viewable from some Google products");
        UIPool.getMenuLabel().setText("GOOGLE PLAY");

        setOnVisibleForInnerScreen();
        UIPool.getMessageBackground().setVisible(true);
        UIPool.getGooglePlayButton().setVisible(false);
        UIPool.getSignInButton().setVisible(true);
    }

    public void setSignedScreen(){
        UIPool.getMessageBackground().setText("Welcome!\nHere you can see your Flow Rush achievements and manage your saved games");
        UIPool.getMenuLabel().setText("GOOGLE PLAY");

        setOnVisibleForInnerScreen();
        UIPool.getMessageBackground().setVisible(true);
        UIPool.getGooglePlayButton().setVisible(false);
        UIPool.getShowSnapshotsButton().setVisible(true);
        UIPool.getShowAchievementsButton().setVisible(true);
        UIPool.getSignOutButton().setVisible(true);
    }

    private void setOnVisibleForInnerScreen(){
        UIPool.getInnerLayout().setVisible(true);
        UIPool.getCloseButton().setVisible(true);
        UIPool.getMenuLabel().setVisible(true);

        if(FlowRush.isPlayServicesAvailable()) {
            UIPool.getGooglePlayButton().setVisible(false);
        }
        UIPool.getAuthorsButton().setVisible(false);
        UIPool.getSupportUsSmallButton().setVisible(false);
        UIPool.getPlayButton().setVisible(false);
        UIPool.getLevelsButton().setVisible(false);
        UIPool.getExitButton().setVisible(false);
        Timer.instance().clear();
        UIPool.getSoundButton().setVisible(false);
        UIPool.getPackGroup().setVisible(false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        hudStage.act(Gdx.graphics.getDeltaTime());
        hudStage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void pause() {
        FlowRush.logDebug("Main screen pause() method called");
    }

    @Override
    public void resume() {
        FlowRush.logDebug("Main screen resume() method called");
    }

    @Override
    public void dispose() {
        FlowRush.logDebug("MenuScreen dispose() called");
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}