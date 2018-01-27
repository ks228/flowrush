package com.blackhornetworkshop.flowrush;

import android.content.Intent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.blackhornetworkshop.flowrush.ex.FlowRushInitializeException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.example.games.basegameutils.GameHelper;

//Created by TScissors

public class FlowRushGameHelperListener implements GameHelper.GameHelperListener {

    private GameHelper gameHelper;
    private AsyncExecutor asyncExecutor;
    private AndroidLauncher androidLauncher;
    private PlayServices playServices;

    private static final int RC_SAVED_GAMES = 9009;

    public FlowRushGameHelperListener(GameHelper gameHelper, AndroidLauncher androidLauncher, AsyncExecutor asyncExecutor, FlowRushPlayServices playServices){
        this.gameHelper = gameHelper;
        this.asyncExecutor = asyncExecutor;
        this.androidLauncher = androidLauncher;
        this.playServices = playServices;
    }

    @Override
    public void onSignInFailed() {
        FlowRushLogger.logDebug("Sign in Google Play Services is failed");
    }
    @Override
    public void onSignInSucceeded() {
        if (!Gdx.files.local("save.json").exists()) { //если файл настроек отсутствует (то есть игра только что была создана)
            FlowRushLogger.logDebug("save.json is not exists, attempt to download from the server");
            asyncExecutor.submit(new AsyncTask<Void>() {
                public Void call() {
                    FlowRushLogger.logDebug("Async task is started. Check the Play Services snapshots");
                    Snapshots.LoadSnapshotsResult result = Games.Snapshots.load(gameHelper.getApiClient(), false).await();
                    if(result.getStatus().isSuccess()) {
                        FlowRushLogger.logDebug("Snapshots on the server: "+result.getSnapshots().getCount());
                        if(result.getSnapshots().getCount()!=0){
                            FlowRushLogger.logDebug("Display available snapshots ui.");
                            showSavedGamesUI();
                        }else{
                            FlowRushLogger.logDebug("It's a first Play Services snapshot");
                            String snapshotName = androidLauncher.flowRush.save.getUniqSnapshotName();
                            byte[] bytes= androidLauncher.saveToGsonToBytes(androidLauncher.flowRush.save);
                            androidLauncher.writeSnapshot(snapshotName, bytes, "FlowRush game save");
                        }
                    }else{
                        FlowRushLogger.logError("Error loading snapshot", new FlowRushInitializeException("LoadSnapShotResult.isSuccess() == false"));
                    }
                    return null;
                }
            });
        } else { //если файл настроек есть
            FlowRushLogger.logDebug("Loading local save");

            androidLauncher.checkAndSave(true);
            for (int x = 0; x < 10; x++) { //каждый раз когда залогиниться проверяем ачивки
                if (androidLauncher.flowRush.save.getAchievements()[x]) {
                    playServices.unlockAchievement(x + 1);
                }
            }
        }
    }

    void showSavedGamesUI() {
        int maxNumberOfSavedGamesToShow = 3;
        Intent savedGamesIntent = Games.Snapshots.getSelectSnapshotIntent(gameHelper.getApiClient(), "Flow Rush Saves", true, true, maxNumberOfSavedGamesToShow);
        androidLauncher.startActivityForResult(savedGamesIntent, RC_SAVED_GAMES);
    }
}
