package com.blackhornetworkshop.flowrush;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.blackhornetworkshop.flowrush.initialization.SavedGame;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.example.games.basegameutils.GameHelper;

import java.io.IOException;

import static com.google.android.gms.internal.zzs.TAG;

public class AndroidLauncher extends AndroidApplication implements PlayServices {

    private final AndroidLauncher androidLauncher;
    private GameHelper gameHelper;
    private FlowRush flowRush;

    private static final int RC_SAVED_GAMES = 9009;

    AsyncExecutor asyncExecutor;
    AsyncResult<Void> task;

    private SavedGame savedGameOnline;
    byte[] data;

    public AndroidLauncher() {
        this.androidLauncher = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;

        final AndroidSide androidSide = new AndroidSideConcrete(this);
        flowRush = new FlowRush(androidSide, androidLauncher);

        asyncExecutor = flowRush.executor;
        initialize(flowRush, config);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES_AND_SNAPSHOT);
        gameHelper.setConnectOnStart(false);
        gameHelper.enableDebugLog(false); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ????????????????????????????????????????????????????????????
        gameHelper.createApiClientBuilder();
        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() { //если не получилось залогиниться в плей сервис
            }
            @Override
            public void onSignInSucceeded() {//если получилось залогиниться в плей сервис
                if (!Gdx.files.local("save.json").exists()) { //если файл настроек отсутствует (то есть игра только что была создана)
                    task = asyncExecutor.submit(new AsyncTask<Void>() {
                        public Void call() {//запускаем в отдельном потоке потому что в потоке UI нельзя
                            //System.out.println("task async start check save if true open saveUi");
                            Snapshots.LoadSnapshotsResult result = Games.Snapshots.load(gameHelper.getApiClient(), false).await();
                            if(result.getStatus().isSuccess()) { //если найдены сохранения, тогда открываем UI save
                                //System.out.println("Shapshot count: "+result.getSnapshots().getCount());
                                //if (result.getStatus().getStatusCode() != GamesStatusCodes.STATUS_GAME_NOT_FOUND) {
                                if(result.getSnapshots().getCount()!=0){
                                    //System.out.println("Show save game activity");
                                    showSavedGamesUI();
                                }else{ //иначе ничего не делаем файл настроек используется новый уже создан в флоураш
                                    //System.out.println("don't show ui, new game save");
                                    writeSnapshotAsync(saveToGsonToBytes(flowRush.save));
                                    //writeSnapshot(flowRush.save.getUniqSaveGameName(), saveToGsonToBytes(flowRush.save), "Last saved game"); !!!!!!!!!! ADDED IN 1.04 !!!!!!!!!!
                                }
                            }else{
                                Log.w(TAG, "load snapshot failed");
                            }
                            return null;
                        }
                    });
                } else { //если файл настроек есть
                    Log.w(TAG, "load exist save local");
                    checkAndSave(true);
                    for (int x = 0; x < 10; x++) { //каждый раз когда залогиниться проверяем ачивки
                        if (flowRush.save.getAchievements()[x]) {
                            unlockAchievement(x + 1);
                        }
                    }
                }
            }
        };
        gameHelper.setup(gameHelperListener);
    }

    private void showSavedGamesUI() {
        int maxNumberOfSavedGamesToShow = 3;
        Intent savedGamesIntent = Games.Snapshots.getSelectSnapshotIntent(gameHelper.getApiClient(), "Flow Rush Saves", true, true, maxNumberOfSavedGamesToShow);
        startActivityForResult(savedGamesIntent, RC_SAVED_GAMES);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent != null) {
            if (intent.hasExtra(Snapshots.EXTRA_SNAPSHOT_METADATA)) {
                // Load the game data from the Snapshot
                SnapshotMetadata snapshotMetadata = (SnapshotMetadata) intent.getParcelableExtra(Snapshots.EXTRA_SNAPSHOT_METADATA);
                String uniqueName = snapshotMetadata.getUniqueName();
                loadCloudSaveAndWriteToLocal(uniqueName);

                //DELETED IN 1.04 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                /*Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        flowRush.getScreen().dispose();
                        flowRush.setLogoScreen();
                    }
                });*/
                //DELETED IN 1.04 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            } else if (intent.hasExtra(Snapshots.EXTRA_SNAPSHOT_NEW)) {
                writeSnapshotAsync(saveToGsonToBytes(flowRush.save));
                flowRush.saveSaveFile();
            }
        }

        super.onActivityResult(requestCode, resultCode, intent);
        gameHelper.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void writeSnapshotAsync(final byte[] data) {
        this.data = data;
        task = asyncExecutor.submit(new AsyncTask<Void>() {
            public Void call() {
                //System.out.println("task async start save");
                //CHANGED !!!!!!
                writeSnapshot(flowRush.save.getUniqSaveGameName(), data, "Last saved game");
                return null;
            }
        });
    }


    private void loadCloudSaveAndWriteToLocal(final String snapshotName){
        //System.out.println("load cloud save and write to local called method");
        task = asyncExecutor.submit(new AsyncTask<Void>() {
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
                            flowRush.save = flowRush.gson.fromJson(string, SavedGame.class);
                            flowRush.saveSaveFile();
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    flowRush.getScreen().hide();
                                    flowRush.setLogoScreen();
                                }
                            });
                        }
                        else {
                            System.out.println("SAVED GAME IS NULL OR CONNECTION LOST");
                        }

                    } catch (IOException e) {
                        Log.w(TAG, "Error while reading Snapshot.");
                    }
                } else {
                    Log.w(TAG, "Error while loading: " + result.getStatus().getStatusCode());
                }
                return null;
            }
        });

    }


    public void loadCloudSaveAndWriteToServer(final String snapshotName, final boolean onStart) { //для проверки загружаем с сервера с помощью этого метода
        //System.out.println("calling loadCloudSaveAndWriteToServer from server");
        task = asyncExecutor.submit(new AsyncTask<Void>() {
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
                        savedGameOnline = flowRush.gson.fromJson(string, SavedGame.class);
                        if(savedGameOnline!=null) {
                            //System.out.println("String savedgameonline is not null");
                            checkSavesBeforeLoadToCloud(flowRush.save, savedGameOnline, onStart);
                        }else{
                            //System.out.println("Write new SNAPSHOT");
                            writeSnapshotAsync(saveToGsonToBytes(flowRush.save));
                        }
                    } catch (IOException e) {
                        Log.w(TAG, "Error while reading Snapshot.");
                    }
                } else {
                    Log.w(TAG, "Error while loading: " + result.getStatus().getStatusCode());
                }
                return null;
            }
        });
    }


    public void loadCloudSaveAndCheck(final String snapshotName) { //для загрузки с сервера и проверки
        task = asyncExecutor.submit(new AsyncTask<Void>() {
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
                        data = snapshot.getSnapshotContents().readFully();
                        savedGameOnline = flowRush.gson.fromJson(new String(data), SavedGame.class);
                        //чекаем сверяем и записываем большие значения
                        checkSavesBeforeLoadFromCloud(flowRush.save, savedGameOnline);
                        flowRush.saveSaveFile();
                    } catch (IOException e) {
                        Log.w(TAG, "Error while reading Snapshot.");

                    }
                } else {
                    Log.w(TAG, "Error while loading: " + result.getStatus().getStatusCode());
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

    public void checkAndSave(boolean onStart){ //для вызова сохраненния. загружает с сервера, проверяет, и обновляет прогресс на сервере если он есть
        //System.out.println("calling check and save");

        //CHANGED !!!!!
        if (isOnline()) {
            loadCloudSaveAndWriteToServer(flowRush.save.getUniqSaveGameName(), onStart);
        }
    }

    //NEW METHOD IN 1.04 !!!!!
    public boolean isOnline() {
        final NetworkInfo activeNetworkInfo = ((ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            if (this.gameHelper.getApiClient().isConnected()) {
                System.out.println("playservices connected and internet is on");
                return true;
            }
            System.out.println("playservices not connected, internet is on");
            this.gameHelper.getApiClient().connect();
        }
        System.out.println("internet is off");
        return false;
    }
    //NEW METHOD IN 1.04 !!!!

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
            writeSnapshotAsync(saveToGsonToBytes(savedGameOnline));
            //System.out.println("update on server");
        }else if(onStart){
            //System.out.println("load update from server");
            loadCloudSaveAndCheck(flowRush.save.getUniqSaveGameName()); //каждый раз при запуске, если файл присутствует save чекаем прогресс
        }
    }

    public byte[] saveToGsonToBytes(SavedGame save){
        return flowRush.gson.toJson(save).getBytes();
    }

    //CHANGED TO VOID TYPE HERE !!
    private void writeSnapshot(String newSnapshotFilename, byte[] data, String desc) {
        Log.w(TAG, "write snapshot method call");
        Snapshots.OpenSnapshotResult result = Games.Snapshots.open(gameHelper.getApiClient(), newSnapshotFilename, true).await();
        Log.w(TAG, "after calling open");
        // Check the result of the open operation
        if (result.getStatus().isSuccess()) {
            Log.w(TAG, "open result is success");

            Snapshot snapshot = result.getSnapshot();
            snapshot.getSnapshotContents().writeBytes(data);

            // Create the change operation
            SnapshotMetadataChange metadataChange = new
                    SnapshotMetadataChange.Builder()
                    //.setCoverImage(coverImage) DELETED  !!!
                    .setDescription(desc)
                    .build();

            // Commit the operation
            //return Games.Snapshots.commitAndClose(gameHelper.getApiClient(), snapshot, metadataChange); // DELETED
            // return
        } else {
            Log.w(TAG, "open result is not success");
            //return null;// DELETED
        }
    }

    @Override
    public void unlockAchievement(int num) {
        Log.w(TAG, "unlock achievement num " + num);
        String s;
        switch (num) {
            case 1:
                s = getString(R.string.achievement_a_good_start);
                break;
            case 2:
                s = getString(R.string.achievement_dove);
                break;
            case 3:
                s = getString(R.string.achievement_question_answered);
                break;
            case 4:
                s = getString(R.string.achievement_great_idea);
                break;
            case 5:
                s = getString(R.string.achievement_mission_possible);
                break;
            case 6:
                s = getString(R.string.achievement_flying);
                break;
            case 7:
                s = getString(R.string.achievement_source_of_knowledge_is_found);
                break;
            case 8:
                s = getString(R.string.achievement_the_end);
                break;
            default:
                s = null;
                break;
        }
        Games.Achievements.unlock(gameHelper.getApiClient(), s);
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }

    @Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void disconnectGameHelper() {
        gameHelper.disconnect();
    }
}