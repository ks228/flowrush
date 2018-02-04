package com.blackhornetworkshop.flowrush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.blackhornetworkshop.flowrush.FRConstants;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.initialization.LevelLoader;
import com.blackhornetworkshop.flowrush.ui.UIPool;
import com.blackhornetworkshop.flowrush.initialization.LevelGroupCreator;

//Created by TScissors. Экран меню игры

public class MenuScreen implements Screen {

    private static MenuScreen instance;

    private Stage hudStage;

    private InputMultiplexer inputMultiplexer;

    public static MenuScreen getInstance(){
        if(instance == null) {
            instance = new MenuScreen();
            FlowRush.logDebug("MenuScreen is initialized. Return new instance");
        }else{
            FlowRush.logDebug("MenuScreen is already initialized. Return existing instance");
        }
        return instance;
    }

    private MenuScreen() {}

    @Override
    public void show() {
        FlowRush.logDebug("Main menu screen show() method called");

        //Начальный уровень берем из файла сохранения
        LevelLoader.getInstance().setLvl(FlowRush.getInstance().save.getCurrentPack(), FlowRush.getInstance().save.getCurrentLvl());

        //Создаем сцену каждый раз при переключении на экран
        hudStage = FlowRush.getInstance().getHudStage();

        //Common sound button
        UIPool.getSoundButton().setPosition(Gdx.graphics.getHeight()*0.02f, Gdx.graphics.getHeight()*0.02f);
        UIPool.getSoundButton().setVisible(true);

        //Добавляем всех актеров на сцену
        hudStage.addActor(UIPool.getWhiteBackActor());
        hudStage.addActor(UIPool.getWhiteBackActorTop());
        hudStage.addActor(UIPool.getBackgroundAnimation());
        hudStage.addActor(UIPool.getPlayButton());
        hudStage.addActor(UIPool.getLvlButton());
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
        hudStage.addActor(UIPool.getLevelGroup());
        hudStage.addActor(UIPool.getCloseButton());
        hudStage.addActor(UIPool.getMenuLabel());

        //Google Play
        if(FlowRush.isPlayServicesAvailable) {
            hudStage.addActor(UIPool.getGooglePlayButton());
            hudStage.addActor(UIPool.getSignInButton());
            hudStage.addActor(UIPool.getSignOutButton());
            hudStage.addActor(UIPool.getShowSnapshotsButton());
            hudStage.addActor(UIPool.getShowAchievementsButton());
        }

        //Заливаем фон
        Gdx.gl.glClearColor(0.26f, 0.64f, 0.87f, 1);


        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(0, FlowRush.getInstance().oneTouchProcessor);
        inputMultiplexer.addProcessor(1, FlowRush.getInstance().getHudStage());
        Gdx.input.setInputProcessor(inputMultiplexer);

        resume();
    }


    @Override
    public void hide() {
        FlowRush.logDebug("Main menu screen hide() method called");
        hudStage.clear();
    }

    public void selectLevelScreen(int pack){
        FlowRush.getInstance().screenType = FRConstants.ScreenType.MAIN_MENU_LVL_CHOICE;

        setOnVisibleForInnerScreen();
        UIPool.getMenuLabel().setText("SELECT LEVEL");
        LevelGroupCreator.getInstance().setupLevelGroup(UIPool.getLevelGroup(), pack);
        UIPool.getLevelGroup().setVisible(true);
    }

    public void setPackChoiseScreen(){
        FlowRush.getInstance().screenType = FRConstants.ScreenType.MAIN_MENU_PACK_CHOICE;
        //System.out.println("screen main menu choose pack screen type 24");
        UIPool.getMenuLabel().setText("SELECT PACK");

        setOnVisibleForInnerScreen();
        UIPool.getPackGroup().setVisible(true);
    }

    public void setSupportUsScreen(){
        FlowRush.getInstance().screenType = FRConstants.ScreenType.MAIN_MENU_SUPPORT_US;
       // System.out.println("screen main menu support us type 23");

        setOnVisibleForInnerScreen();
        UIPool.getSupportUsButton().setVisible(false);
        UIPool.getRateUsButton().setVisible(false);
        UIPool.getFeedButton().setVisible(false);

        /**messageBack.setText("\nPlease support our indie development team. Remove ads or share our game with your friends .\n\nYou help us to grow.\nThank you!");*/
        UIPool.getMessageBackground().setText("Please support our indie development team. Share our game with your friends.\n\nYou help us to grow.\nThank you!");
        UIPool.getMenuLabel().setText("SUPPORT US");

        UIPool.getMessageBackground().setVisible(true);
        //supportUsLabel.setVisible(true);
        UIPool.getSocialNetworkBackground().setVisible(true);
        /**removeAds.setVisible(true);*/
        UIPool.getTwitterButton().setVisible(true);
        UIPool.getFacebookButton().setVisible(true);
        UIPool.getVkButton().setVisible(true);
    }

    public void setAuthorsScreen(){
        FlowRush.getInstance().screenType = FRConstants.ScreenType.MAIN_MENU_AUTHORS;
        //System.out.println("screen main menu authors type 22");

        UIPool.getMessageBackground().setText("development\nTIMUR SCISSORS\n\ndesign\nSONYA KOVALSKI\n\nmusic\nERIC HOPTON");
        UIPool.getMenuLabel().setText("AUTHORS");
        setOnVisibleForInnerScreen();

        UIPool.getMessageBackground().setVisible(true);
        UIPool.getSupportUsButton().setVisible(true);
        UIPool.getRateUsButton().setVisible(true);
        UIPool.getFeedButton().setVisible(true);
    }

    public void setSignInScreen(){
        FlowRush.getInstance().screenType = FRConstants.ScreenType.MAIN_MENU_GOOGLE_PLAY;

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
        FlowRush.getInstance().screenType = FRConstants.ScreenType.MAIN_MENU_LOAD_SAVED_GAME;

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

        if(FlowRush.isPlayServicesAvailable) {
            UIPool.getGooglePlayButton().setVisible(false);
        }
        UIPool.getAuthorsButton().setVisible(false);
        UIPool.getSupportUsSmallButton().setVisible(false);
        UIPool.getPlayButton().setVisible(false);
        UIPool.getLvlButton().setVisible(false);
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
    }

    @Override
    public void resume() {
        FlowRush.getInstance().screenType = FRConstants.ScreenType.MAIN_MENU;
        //System.out.println("screen main menu type 21");

        UIPool.getMenuLabel().setVisible(false);
        UIPool.getLevelGroup().setVisible(false);
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

        if(FlowRush.isPlayServicesAvailable) {
            UIPool.getGooglePlayButton().setVisible(true);
            UIPool.getSignInButton().setVisible(false);
            UIPool.getShowSnapshotsButton().setVisible(false);
            UIPool.getShowAchievementsButton().setVisible(false);
            UIPool.getSignOutButton().setVisible(false);
        }

        UIPool.getPlayButton().setVisible(true);
        UIPool.getLvlButton().setVisible(true);
        UIPool.getSoundButton().setVisible(true);
        UIPool.getAuthorsButton().setVisible(true);
        UIPool.getSupportUsSmallButton().setVisible(true);
    }

    @Override
    public void dispose() {
        FlowRush.logDebug("MenuScreen dispose() called");
    }
}