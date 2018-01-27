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
import com.badlogic.gdx.utils.async.AsyncTask;
import com.blackhornetworkshop.flowrush.ex.FlowRushInitializeException;
import com.blackhornetworkshop.flowrush.initialization.SavedGame;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.example.games.basegameutils.GameHelper;

import java.io.IOException;

public class AndroidLauncher extends AndroidApplication {

    private static final String TAG = "FlowRush";

    private GameHelper gameHelper;
    FlowRushPlayServices playServices;
    FlowRush flowRush;

    AsyncExecutor asyncExecutor;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        //AndroidLauncher initialization
        super.onCreate(savedInstanceState);
        AndroidSide androidSide = new AndroidSideConcrete(this);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;

        asyncExecutor = new AsyncExecutor(20);

        //Game services helper initialization
        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES_AND_SNAPSHOT);
        gameHelper.setConnectOnStart(false);
        gameHelper.enableDebugLog(FlowRushLogger.isDebug());
        gameHelper.createApiClientBuilder();
        gameHelper.setup(new FlowRushGameHelperListener(gameHelper, this, asyncExecutor, playServices));

        //FlowRushPlayServices initialization
        playServices = new FlowRushPlayServices(this, gameHelper, asyncExecutor);

        //Game initialization
        try {
            FlowRushLogger.logDebug("FlowRush initialization");
            FlowRush.initialize(androidSide, playServices, asyncExecutor);
            flowRush = FlowRush.getInstance();
            FlowRushLogger.logDebug("FlowRush initialization is successful");
        }catch (FlowRushInitializeException ex) {
            FlowRushLogger.logError("Initialization error", ex);
        }


        initialize(flowRush, config);
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
                playServices.loadCloudSaveAndWriteToLocal(uniqueName);
            } else if (intent.hasExtra(Snapshots.EXTRA_SNAPSHOT_NEW)) {
                playServices.writeSnapshotAsync(saveToGsonToBytes(flowRush.save));
                flowRush.saveSaveFile();
            }
        }

        super.onActivityResult(requestCode, resultCode, intent);
        gameHelper.onActivityResult(requestCode, resultCode, intent);
    }

    public void checkAndSave(boolean onStart){ //для вызова сохраненния. загружает с сервера, проверяет, и обновляет прогресс на сервере если он есть
        //System.out.println("calling check and save");

        //CHANGED !!!!!
        if (isOnline()) {
            playServices.loadCloudSaveAndWriteToServer(flowRush.save.getUniqSnapshotName(), onStart);
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

    public byte[] saveToGsonToBytes(SavedGame save){
        return flowRush.gson.toJson(save).getBytes();
    }

    //CHANGED TO VOID TYPE HERE !!
    void writeSnapshot(String newSnapshotFilename, byte[] data, String desc) {
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
}