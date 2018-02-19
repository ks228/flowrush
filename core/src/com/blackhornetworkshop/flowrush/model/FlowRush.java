package com.blackhornetworkshop.flowrush.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.blackhornetworkshop.flowrush.controller.LevelController;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.controller.OneTouchProcessor;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;
import com.blackhornetworkshop.flowrush.view.screens.MenuScreen;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;
import com.google.gson.Gson;


public class FlowRush extends Game {

    private static final FlowRush instance = new FlowRush();

    private static AndroidHelper androidHelper;
    private static PlayServices playServices;
    private static OneTouchProcessor oneTouchProcessor;
    private static Gson gson;

    private static GamePreferences preferences;
    private static SavedGame save;

    private static SpriteBatch batch;
    private static Stage hexesStage, hudStage;

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

    @Override
    public void create() {
        logDebug("FlowRush create() method called");

        batch = new SpriteBatch();

        ScreenViewport screenViewport = new ScreenViewport();
        hexesStage = new Stage(screenViewport, batch);
        hudStage = new Stage(screenViewport, batch);

        Gdx.input.setCatchBackKey(true);
        oneTouchProcessor = new OneTouchProcessor();

        gson = new Gson();

        LevelController.setPacks(FRFileHandler.loadPacks());

        if (Gdx.files.local("preferences.json").exists()) {
            logDebug("Load an existing file preferences.json");
            preferences = FRFileHandler.loadPreferences();
        } else {
            logDebug("A new file preferences.json was created");
            preferences = new GamePreferences();
        }

        if (Gdx.files.local("save.json").exists()) {
            androidHelper.logDebug("Load an existing file save.json");
            save = FRFileHandler.loadSavedGame();
        } else {
            save = new SavedGame();
            save.setUniqSaveName();
            logDebug("A new file save.json was created");
        }
        FRAssetManager.initialize();
        FRAssetManager.loadLogos();

        ScreenManager.setLogoBHWScreen();
    }

    public static void dayNightShift(){
        logDebug("FlowRush dayNightShift() called");

        hudStage.clear();

        UIPool.reinialize();

        MenuScreen.getInstance().show();

        ScreenManager.setMenuMainScreen();
    }

    public static void checkAchievements() {
        logDebug("FlowRush checkAchievements() method called");
        for (int x = 0; x < 5; x++) {
            if (FlowRush.getSave().getLevelsProgress(x) > 1) {
                FlowRush.getInstance().unlockAchievement(1); // FIRST LEVEL IS DONE
                break;
            }
        }

        for(int x = 0; x < 5; x++) {
            if (((x == 0 && FlowRush.getSave().getLevelsProgress(x) > 10) ||
                    (x != 0 && FlowRush.getSave().getLevelsProgress(x) > 1))) {
                FlowRush.getInstance().unlockAchievement(2); // FIRST LEVEL WITH A DOVE IS DONE
                break;
            }
        }

        boolean isAllPacksDone = true;

        for (int x = 0; x < 5; x++) { // CHECK PACKS PROGRESS
            if (!FlowRush.getSave().isPackFinished(x)) {
                isAllPacksDone = false;
            } else {
                FlowRush.getInstance().unlockAchievement(x + 3);
            }
        }

        if (isAllPacksDone)
            FlowRush.getInstance().unlockAchievement(8); // ALL PACKS ARE DONE
    }

    private void unlockAchievement(int num) {
        if (num > 0 && num < 9) {
            if (FlowRush.isPlayServicesAvailable() && playServices.isSignedIn()) {
                playServices.unlockAchievement(num);
            }
        } else {
            androidHelper.logError("No such achievement!", new IllegalArgumentException());
        }
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

    public static SpriteBatch getBatch() {
        return batch;
    }
    public Stage getHexesStage() { return hexesStage; }
    public Stage getHudStage() {
        return hudStage;
    }

    public void pause() {
        logDebug("FlowRush pause() method called");
        getScreen().pause();
        if(FRAssetManager.isMusicLoaded()) {
            FRAssetManager.getBackgroundMusic().pause();
        }
    }

    public void resume() {
        logDebug("FlowRush resume() method called");
        getScreen().resume();
        if (FRAssetManager.isMusicLoaded() && preferences.isSoundOn() && ScreenManager.getCurrentScreen() != FRConstants.ScreenType.LOGO_BHW){
            FRAssetManager.getBackgroundMusic().play();
        }
    }

    public static void logError(String msg, Throwable tr) {
        androidHelper.logError(msg, tr);
    }

    public static void logDebug(String msg) {
        androidHelper.logDebug(msg);
    }

    public void dispose() {
        logDebug("FlowRush dispose() called");
        hexesStage.dispose();
        hudStage.dispose();
        batch.dispose();

        MenuScreen.getInstance().dispose();
        GameScreen.getInstance().dispose();

        FRAssetManager.dispose();
    }
    public static OneTouchProcessor getOneTouchProcessor(){return oneTouchProcessor;}
    public static GamePreferences getPreferences(){return preferences;}
    public static SavedGame getSave(){return save;}
    public static void setSave(SavedGame save){ FlowRush.save = save;}
    public static boolean isPlayServicesAvailable(){return isPlayServicesAvailable;}
}

