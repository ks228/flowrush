package com.blackhornetworkshop.flowrush.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.blackhornetworkshop.flowrush.controller.AndroidHelper;
import com.blackhornetworkshop.flowrush.controller.FRAssetManager;
import com.blackhornetworkshop.flowrush.controller.PlayServices;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.model.GamePreferences;
import com.blackhornetworkshop.flowrush.controller.OneTouchProcessor;
import com.blackhornetworkshop.flowrush.model.SavedGame;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;
import com.blackhornetworkshop.flowrush.view.screens.LogoScreen;
import com.blackhornetworkshop.flowrush.view.screens.MenuScreen;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;
import com.google.gson.Gson;


public class FlowRush extends Game {

    private static final FlowRush instance = new FlowRush();
    private static AndroidHelper androidHelper;
    private static PlayServices playServices;

    //Stages
    private SpriteBatch batch;
    private Stage hexesStage, hudStage;

    //Data
    private static GamePreferences preferences;
    private static SavedGame save;

    // Input
    private static OneTouchProcessor oneTouchProcessor;

    //Utils
    private static Gson gson;

    //Primitives
    private static boolean isPlayServicesAvailable;

    public static FlowRush getInstance(){
        return instance;
    }

    private FlowRush() {}

    public void setup(AndroidHelper androidHelper) {
        FlowRush.androidHelper = androidHelper;
        isPlayServicesAvailable = false;
        logDebug("FlowRush is configured. Without Play Services");
    }

    public void setup(AndroidHelper androidHelper, PlayServices playServices) {
        FlowRush.androidHelper = androidHelper;
        FlowRush.playServices = playServices;
        isPlayServicesAvailable = true;
        logDebug("FlowRush is configured. With Play Services");
    }

    public void create() {
        logDebug("FlowRush onCreate() called");

        batch = new SpriteBatch();

        //Stages
        ScreenViewport screenViewport = new ScreenViewport();
        hexesStage = new Stage(screenViewport, batch);
        hudStage = new Stage(screenViewport, batch);

        //Input
        Gdx.input.setCatchBackKey(true);
        oneTouchProcessor = new OneTouchProcessor();

        //Json
        gson = new Gson();

        //Preferences
        if (Gdx.files.local("preferences.json").exists()) {
            logDebug("Load an existing file preferences.json");
            preferences = gson.fromJson(Gdx.files.local("preferences.json").reader(), GamePreferences.class);
        } else {
            logDebug("A new file preferences.json was created");
            preferences = new GamePreferences();
        }

        //Save file
        if (Gdx.files.local("save.json").exists()) {
            androidHelper.logDebug("Load an existing file save.json");
            save = gson.fromJson(Gdx.files.local("save.json").reader(), SavedGame.class);
        } else {
            save = new SavedGame();
            save.setUniqSaveName();
            logDebug("A new file save.json was created");
        }

        FRAssetManager.loadAssets();
        UIPool.initialize();

        //Start a game
        ScreenManager.setLogoBHWScreen();
    }

    public void unlockAchievement(int num) {
        if (num > 0 && num < 9) {
            save.unlockAchievement(num - 1);
            if (FlowRush.isPlayServicesAvailable() && playServices.isSignedIn()) {
                playServices.unlockAchievement(num);
            }
        } else {
            androidHelper.logError("No such achievement!", new IllegalArgumentException());
        }
    }

    public void savePrefsFile() {
        String string = gson.toJson(preferences);
        FileHandle file = Gdx.files.local("preferences.json");
        file.writeString(string, false);
        //System.out.println("Saved preferences");
    }

    public void saveSaveFile() {
        String string = gson.toJson(save);
        FileHandle file = Gdx.files.local("save.json");
        file.writeString(string, false);
        System.out.println("Saved progress on local");
    }

    public static Gson getGson() {
        return gson;
    }

    public static AndroidHelper getAndroidHelper() {
        return androidHelper;
    }
    public static PlayServices getPlayServices() {
        return playServices;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
    public Stage getHexesStage() { return hexesStage; }
    public Stage getHudStage() {
        return hudStage;
    }

    public void pause() {
        getScreen().pause();
        FRAssetManager.getBackgroundMusic().pause();
    }

    public void resume() {
        getScreen().resume();
        if (preferences.isSoundOn()) FRAssetManager.getBackgroundMusic().play();
    }

    public void render() { super.render(); }

    public void logError(String msg, Throwable tr) {
        androidHelper.logError(msg, tr);
    }

    public static void logDebug(String msg) {
        androidHelper.logDebug(msg);
    }

    public void dispose() {
        hexesStage.dispose();
        hudStage.dispose();
        batch.dispose();

        LogoScreen.getInstance().dispose();
        MenuScreen.getInstance().dispose();
        GameScreen.getInstance().dispose();

        FRAssetManager.getSkin().remove("fontLarge", BitmapFont.class);// REMOVE IS IMPORTANT!
        FRAssetManager.getSkin().remove("fontMid", BitmapFont.class);
        FRAssetManager.getSkin().remove("fontSmall", BitmapFont.class);
        FRAssetManager.dispose();

        logDebug("FlowRush dispose() called");
    }
    public static OneTouchProcessor getOneTouchProcessor(){return oneTouchProcessor;}
    public static GamePreferences getPreferences(){return preferences;}
    public static SavedGame getSave(){return save;}
    public static void loadSave(SavedGame save){ FlowRush.save = save;}
    public static boolean isPlayServicesAvailable(){return isPlayServicesAvailable;}
}

