package com.blackhornetworkshop.flowrush;

//Created by TScissors


import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.blackhornetworkshop.flowrush.ex.FlowRushInitializeException;
import com.blackhornetworkshop.flowrush.initialization.SavedGame;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.example.games.basegameutils.GameHelper;

import java.io.IOException;

public class FlowRushPlayServices implements PlayServices {

    AndroidLauncher androidLauncher;
    GameHelper gameHelper;
    AsyncExecutor asyncExecutor;

    private SavedGame savedGameOnline;

    public FlowRushPlayServices(AndroidLauncher androidLauncher, GameHelper gameHelper, AsyncExecutor asyncExecutor) {
        this.androidLauncher = androidLauncher;
        this.gameHelper = gameHelper;
        this.asyncExecutor = asyncExecutor;
    }

    @Override
    public void signIn() {
        try {
            androidLauncher.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e) {
            FlowRushLogger.logError("Play Services log in failed", e);
        }
    }

    @Override
    public void signOut() {
        try {
            androidLauncher.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        } catch (Exception e) {
            FlowRushLogger.logError("Play Services log out failed", e);
        }
    }

    @Override
    public void unlockAchievement(int num) {
        FlowRushLogger.logDebug("Play Services unlock achievment"+num);
        String s;
        switch (num) {
            case 1:
                s = androidLauncher.getString(R.string.achievement_a_good_start);
                break;
            case 2:
                s = androidLauncher.getString(R.string.achievement_dove);
                break;
            case 3:
                s = androidLauncher.getString(R.string.achievement_question_answered);
                break;
            case 4:
                s = androidLauncher.getString(R.string.achievement_great_idea);
                break;
            case 5:
                s = androidLauncher.getString(R.string.achievement_mission_possible);
                break;
            case 6:
                s = androidLauncher.getString(R.string.achievement_flying);
                break;
            case 7:
                s = androidLauncher.getString(R.string.achievement_source_of_knowledge_is_found);
                break;
            case 8:
                s = androidLauncher.getString(R.string.achievement_the_end);
                break;
            default:
                s = null;
                break;
        }
        Games.Achievements.unlock(gameHelper.getApiClient(), s);
    }

    @Override
    public void writeSnapshotAsync(final byte[] data) {
        asyncExecutor.submit(new AsyncTask<Void>() {
            public Void call() {
                FlowRushLogger.logDebug("Asynchronous snapshot writing is started");
                androidLauncher.writeSnapshot(androidLauncher.flowRush.save.getUniqSnapshotName(), data, "Last saved game");
                return null;
            }
        });
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }

    @Override
    public void checkAndSave(boolean onStart) {
        FlowRushLogger.logDebug("Calling checkAndSave()");
        if (androidLauncher.isOnline()) {
            loadCloudSaveAndWriteToServer(androidLauncher.flowRush.save.getUniqSnapshotName(), onStart);
        }
    }

    @Override
    public void disconnectGameHelper() {
        gameHelper.disconnect();
    }

    public void loadCloudSaveAndWriteToServer(final String snapshotName, final boolean onStart) { //для проверки загружаем с сервера с помощью этого метода
        //System.out.println("calling loadCloudSaveAndWriteToServer from server");
        asyncExecutor.submit(new AsyncTask<Void>() {
            public Void call() {
                //System.out.println("task async start load");
                // Open the saved game using its name.
                Snapshots.OpenSnapshotResult result = Games.Snapshots.open(gameHelper.getApiClient(), snapshotName, true).await();

                // Check the result of the open operation
                if (result.getStatus().isSuccess()) {
                    //System.out.println("result of open snapshot "+result.getStatus().getStatusCode());
                    Snapshot snapshot = result.getSnapshot();
                    // Read the byte content of the saved game.
                    try {
                        //data = snapshot.getSnapshotContents().readFully();
                        String string = new String(snapshot.getSnapshotContents().readFully());
                        savedGameOnline = androidLauncher.flowRush.gson.fromJson(string, SavedGame.class);
                        if(savedGameOnline!=null) {
                            //System.out.println("String savedgameonline is not null");
                            checkSavesBeforeLoadToCloud(androidLauncher.flowRush.save, savedGameOnline, onStart);
                        }else{
                            //System.out.println("Write new SNAPSHOT");
                            writeSnapshotAsync(androidLauncher.saveToGsonToBytes(androidLauncher.flowRush.save));
                        }
                    } catch (IOException e) {
                        FlowRushLogger.logError("Error while reading Snapshot.", new FlowRushInitializeException("loadCloudSaveAndWriteToServer() method error"));
                    }
                } else {
                    FlowRushLogger.logError("Error while loading snapshot: " + result.getStatus().getStatusCode(), new FlowRushInitializeException("loadCloudSaveAndWriteToServer() method error"));
                }
                return null;
            }
        });
    }

    public void loadCloudSaveAndWriteToLocal(final String snapshotName){
        //System.out.println("load cloud save and write to local called method");
        asyncExecutor.submit(new AsyncTask<Void>() {
            public Void call() {
                //System.out.println("task async start load");
                // Open the saved game using its name.

                //THIS CHANGED (BOOLEAN) !
                Snapshots.OpenSnapshotResult result = Games.Snapshots.open(gameHelper.getApiClient(), snapshotName, false).await();


                // Check the result of the open operation
                if (result.getStatus().isSuccess()) {
                    //System.out.println("result of open snapshot "+result.getStatus().getStatusCode());
                    Snapshot snapshot = result.getSnapshot();
                    // Read the byte content of the saved game.
                    try {
                        String string = new String(snapshot.getSnapshotContents().readFully());
                        //CHANGED !!!
                        if (string != null) {
                            androidLauncher.flowRush.save = androidLauncher.flowRush.gson.fromJson(string, SavedGame.class);
                            androidLauncher.flowRush.saveSaveFile();
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    androidLauncher.flowRush.getScreen().hide();
                                    androidLauncher.flowRush.setLogoScreen();
                                }
                            });
                        }
                        else {
                            System.out.println("SAVED GAME IS NULL OR CONNECTION LOST");
                        }

                    } catch (IOException e) {
                        FlowRushLogger.logError("Error while reading Snapshot.", new FlowRushInitializeException("loadCloudSaveAndWriteToLocal() method error"));
                    }
                } else {
                    FlowRushLogger.logError("Error while loading snapshot: " + result.getStatus().getStatusCode(), new FlowRushInitializeException("loadCloudSaveAndWriteToLocal() method error"));
                }
                return null;
            }
        });
    }
    private void checkSavesBeforeLoadFromCloud(final SavedGame savedGame, final SavedGame savedGameOnline){ //проверяем и обновляем локальный файл сохранения
        //для current level
        boolean upCurrentLvl= false;

        //check achievements
        for(int x = 0; x<10; x++) {
            if (savedGameOnline.getAchievements()[x]&&!savedGame.getAchievements()[x]) {
                savedGame.getAchievements()[x]=true;
                //System.out.println("Update achieve "+x+" in save file on local");
            }else{
                //System.out.println("Ancievement "+x+" not need to update on local");
            }
        }
        //check saves
        for(int y = 0; y<5; y++){
            if(savedGame.getLevelsProgress()[y]<savedGameOnline.getLevelsProgress()[y]){
                savedGame.getLevelsProgress()[y]=savedGameOnline.getLevelsProgress()[y];
                //System.out.println("Update level progress in pack "+y+"on local");
                upCurrentLvl = true; //обновляем уровень и пак если загружаемое сохранение с большим прогрессом
            }else{
                //System.out.println("Level progress "+y+" not need to update on local");
            }
        }
        if(upCurrentLvl){
            //System.out.println("Save new current lvl and pack from server");
            savedGame.setCurrentLvl(savedGameOnline.getCurrentLvl());
            savedGame.setCurrentPack(savedGameOnline.getCurrentPack());

        }else{
            //System.out.println("do not update current lvl and pack");
        }
    }

    public void loadCloudSaveAndCheck(final String snapshotName) { //для загрузки с сервера и проверки
        asyncExecutor.submit(new AsyncTask<Void>() {
            public Void call() {
                //System.out.println("task async start load from server and check");
                // Open the saved game using its name.

                //CHANGED BOOLEAN !!!
                Snapshots.OpenSnapshotResult result = Games.Snapshots.open(gameHelper.getApiClient(), snapshotName, false).await();

                // Check the result of the open operation
                if (result.getStatus().isSuccess()) {
                    Snapshot snapshot = result.getSnapshot();
                    // Read the byte content of the saved game.
                    try {
                        savedGameOnline = androidLauncher.flowRush.gson.fromJson(new String(snapshot.getSnapshotContents().readFully()), SavedGame.class);
                        //чекаем сверяем и записываем большие значения
                        checkSavesBeforeLoadFromCloud(androidLauncher.flowRush.save, savedGameOnline);
                        androidLauncher.flowRush.saveSaveFile();
                    } catch (IOException e) {
                        FlowRushLogger.logError("Error while reading Snapshot.", new FlowRushInitializeException("loadCloudSaveAndCheck() method error"));
                    }
                } else {
                    FlowRushLogger.logError("Error while loading snapshot: " + result.getStatus().getStatusCode(), new FlowRushInitializeException("loadCloudSaveAndCheck() method error"));
                }
                return null;
            }
        });
    }

    private void checkSavesBeforeLoadToCloud(final SavedGame savedGame, final SavedGame savedGameOnline, boolean onStart){ //проверяем с состоянием на сервере перед отправкой новой инфы
        //System.out.println("calling checkSavesBeforeLoadToCloud");

        //для current level
        boolean upCurrentLvl= false;

        //check achievements
        for(int x = 0; x<10; x++) {
            if (savedGame.getAchievements()[x]&&!savedGameOnline.getAchievements()[x] ) {
                savedGameOnline.getAchievements()[x]=true;
                //System.out.println("Update achieve "+x+" in server save file");
            }else{
                //System.out.println("Ancievement "+x+" not need to update on server");
            }
        }
        //check saves
        for(int y = 0; y<5; y++){
            if(savedGame.getLevelsProgress()[y]>savedGameOnline.getLevelsProgress()[y]){
                savedGameOnline.getLevelsProgress()[y]=savedGame.getLevelsProgress()[y];
                //System.out.println("Update level progress in pack "+y+" in server save file");
                upCurrentLvl = true; //обновляем уровень и пак если загружаемое сохранение с большим прогрессом
            }else{
                //System.out.println("Level progress in pack "+y+" not need to update on server");
            }
        }
        if(upCurrentLvl){
            savedGameOnline.setCurrentLvl(savedGame.getCurrentLvl());
            savedGameOnline.setCurrentPack(savedGame.getCurrentPack());
            writeSnapshotAsync(androidLauncher.saveToGsonToBytes(savedGameOnline));
            //System.out.println("update on server");
        }else if(onStart){
            //System.out.println("load update from server");
            loadCloudSaveAndCheck(androidLauncher.flowRush.save.getUniqSnapshotName()); //каждый раз при запуске, если файл присутствует save чекаем прогресс
        }
    }
}
