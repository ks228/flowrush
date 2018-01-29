package com.blackhornetworkshop.flowrush;

import android.content.Intent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.blackhornetworkshop.flowrush.ex.FlowRushInitializeException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.snapshot.Snapshots;
//import com.google.example.games.basegameutils.GameHelper;


//Created by TScissors

public class FlowRushGameHelperListener /*implements GameHelper.GameHelperListener */{

    //private GameHelper gameHelper;
    private AsyncExecutor asyncExecutor;
    private AndroidLauncher androidLauncher;
    private FlowRushPlayServices playServices;

    private static final int RC_SAVED_GAMES = 9009;

    /*public FlowRushGameHelperListener(GameHelper gameHelper, AndroidLauncher androidLauncher, AsyncExecutor asyncExecutor, FlowRushPlayServices playServices){
        //this.gameHelper = gameHelper;
        this.asyncExecutor = asyncExecutor;
        this.androidLauncher = androidLauncher;
        this.playServices = playServices;
    }*/

    /*@Override
    public void onSignInFailed() {
        FRLogger.logDebug("Sign in Google Play Services is failed");
    }
    @Override
    public void onSignInSucceeded() {

        //START ACTIVITY FOR RESULT ADD HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        /*FRLogger.logDebug("Sign in success, calling onSignInSucceeded method");
        if (!Gdx.files.local("save.json").exists()) {
            FRLogger.logDebug("save.json is not exists, attempt to download from the server");
            asyncExecutor.submit(new AsyncTask<Void>() { //!!!!!!!!! IMPORTANT, CALLING MUST BE OT ON UI THREAD
                public Void call() {
                    FRLogger.logDebug("Async task is started. Check the Play Services snapshots");
                    Snapshots.LoadSnapshotsResult result = Games.Snapshots.load(gameHelper.getApiClient(), false).await();
                    if(result.getStatus().isSuccess()) {
                        FRLogger.logDebug("Snapshots on the server: "+result.getSnapshots().getCount());
                        if(result.getSnapshots().getCount()!=0){
                            FRLogger.logDebug("Display available snapshots ui.");
                            showSavedGamesUI();
                        }else{
                            FRLogger.logDebug("It's a first Play Services snapshot");
                            String snapshotName = androidLauncher.flowRush.save.getUniqSnapshotName();
                            byte[] bytes= androidLauncher.saveToGsonToBytes(androidLauncher.flowRush.save);
                            androidLauncher.writeSnapshot(snapshotName, bytes, "FlowRush game save");
                        }
                    }else{
                        FRLogger.logError("Error loading snapshot", new FlowRushInitializeException("LoadSnapShotResult.isSuccess() == false"));
                    }
                    return null;
                }
            });
        } else {
            FRLogger.logDebug("File save.json exists, loading local save");

            checkAndSave(true);
            for (int x = 0; x < 10; x++) { //каждый раз когда залогиниться проверяем ачивки
                if (androidLauncher.flowRush.save.getAchievements()[x]) {
                    playServices.unlockAchievement(x + 1);
                }
            }
        }
    }*/

    void showSavedGamesUI() {
        /*int maxNumberOfSavedGamesToShow = 3;
        Intent savedGamesIntent = Games.Snapshots.getSelectSnapshotIntent(gameHelper.getApiClient(), "Flow Rush Saves", true, true, maxNumberOfSavedGamesToShow);
        androidLauncher.startActivityForResult(savedGamesIntent, RC_SAVED_GAMES);*/
    }

    public void checkAndSave(boolean onStart){ //для вызова сохраненния. загружает с сервера, проверяет, и обновляет прогресс на сервере если он есть
        /*//System.out.println("calling check and save");

        //CHANGED !!!!!
        if (androidLauncher.isOnline()) {
            playServices.loadCloudSaveAndWriteToServer(androidLauncher.flowRush.save.getUniqSnapshotName(), onStart);
        }*/
    }
}
