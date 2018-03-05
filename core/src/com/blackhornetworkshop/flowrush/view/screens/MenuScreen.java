package com.blackhornetworkshop.flowrush.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.controller.FlowRush;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;

import static com.blackhornetworkshop.flowrush.model.FRConstants.AUTHORS_MSG;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SIGNED_MSG;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SIGN_IN_MSG;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SUPPORT_MSG;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SUPPORT_MSG_ADS_REMOVED;

//Created by TScissors.

public class MenuScreen implements Screen, FRScreen {

    private static MenuScreen instance;
    private Stage hudStage;
    private boolean isActive;

    private InputMultiplexer inputMultiplexer;
    public static MenuScreen getInstance(){
        if(instance == null) instance = new MenuScreen();
        return instance;
    }

    private MenuScreen() {
        inputMultiplexer = new InputMultiplexer();
    }

    @Override
    public void show() {
        FlowRush.logDebug("Main menu screen show() method called");

        isActive = true;

        hudStage = FlowRush.getInstance().getHudStage();

        UIPool.getSoundButton().setPosition(SCREEN_HEIGHT*0.02f, SCREEN_HEIGHT*0.02f);
        UIPool.getSoundButton().setVisible(true);

        hudStage.addActor(UIPool.getTriangleBackground());
        hudStage.addActor(UIPool.getTriangleBackgroundTop());
        hudStage.addActor(UIPool.getBackgroundAnimation());
        hudStage.addActor(UIPool.getPlayButton());
        hudStage.addActor(UIPool.getLevelsButton());
        hudStage.addActor(UIPool.getExitButton());
        hudStage.addActor(UIPool.getDayNightButton());
        hudStage.addActor(UIPool.getAuthorsButton());
        hudStage.addActor(UIPool.getSoundButton());
        hudStage.addActor(UIPool.getSupportUsSmallButton());
        hudStage.addActor(UIPool.getInnerLayout());
        hudStage.addActor(UIPool.getLabelContainer());
        hudStage.addActor(UIPool.getPackGroup());
        hudStage.addActor(UIPool.getSupportUsButton());
        hudStage.addActor(UIPool.getRateUsButton());
        hudStage.addActor(UIPool.getFeedButton());
        if(!FlowRush.getPreferences().isAdsRemoved()) {
            hudStage.addActor(UIPool.getRemoveAdsButton());
        }
        hudStage.addActor(UIPool.getSocialNetworksButton());
        hudStage.addActor(UIPool.getWebsiteButton());
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


        inputMultiplexer.addProcessor(0, FlowRush.getOneTouchProcessor());
        inputMultiplexer.addProcessor(1, FlowRush.getInstance().getHudStage());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    @Override
    public void hide() {
        FlowRush.logDebug("Main menu screen hide() method called");
        inputMultiplexer.clear();
        hudStage.clear();
        isActive = false;
    }

    public void setMainMenuScreen(){
        if(FlowRush.getPreferences().isNightMode()){
            Gdx.gl.glClearColor(0.0745f, 0.0941f, 0.1059f, 1);
        }else{
            Gdx.gl.glClearColor(0.26f, 0.64f, 0.87f, 1);
        }

        UIPool.getMenuLabel().setVisible(false);
        UIPool.getLevelNumbersGroup().setVisible(false);
        UIPool.getInnerLayout().setVisible(false);
        UIPool.getCloseButton().setVisible(false);
        UIPool.getMessageBackground().setVisible(false);
        UIPool.getPackGroup().setVisible(false);
        UIPool.getSupportUsButton().setVisible(false);
        UIPool.getRateUsButton().setVisible(false);
        UIPool.getFeedButton().setVisible(false);
        UIPool.getSocialNetworksButton().setVisible(false);
        if(!FlowRush.getPreferences().isAdsRemoved()) {
            UIPool.getRemoveAdsButton().setVisible(false);
        }
        UIPool.getWebsiteButton().setVisible(false);

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
        UIPool.getDayNightButton().setVisible(true);
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

        UIPool.getMenuLabel().setText("SUPPORT US");

        UIPool.getMessageBackground().setVisible(true);
        UIPool.getSocialNetworksButton().setVisible(true);

        if(!FlowRush.getPreferences().isAdsRemoved()) {
            UIPool.getRemoveAdsButton().setVisible(true);
            UIPool.getMessageBackground().setText(SUPPORT_MSG);
        }else{
            UIPool.getMessageBackground().setText(SUPPORT_MSG_ADS_REMOVED);
        }
        UIPool.getWebsiteButton().setVisible(true);
    }

    public void setAuthorsScreen(){
        UIPool.getMessageBackground().setText(AUTHORS_MSG);
        UIPool.getMenuLabel().setText("AUTHORS");
        setOnVisibleForInnerScreen();

        UIPool.getMessageBackground().setVisible(true);
        UIPool.getSupportUsButton().setVisible(true);
        UIPool.getRateUsButton().setVisible(true);
        UIPool.getFeedButton().setVisible(true);
    }

    public void setSignInScreen(){
        UIPool.getMessageBackground().setText(SIGN_IN_MSG);
        UIPool.getMenuLabel().setText("GOOGLE PLAY");

        setOnVisibleForInnerScreen();
        UIPool.getMessageBackground().setVisible(true);
        UIPool.getGooglePlayButton().setVisible(false);
        UIPool.getSignInButton().setVisible(true);
    }

    public void setSignedScreen(){
        UIPool.getMessageBackground().setText(SIGNED_MSG);
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
        UIPool.getDayNightButton().setVisible(false);
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
        ScreenManager.setMenuMainScreen();
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