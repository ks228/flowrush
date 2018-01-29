package com.blackhornetworkshop.flowrush;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.blackhornetworkshop.flowrush.ex.FlowRushInitializeException;
import com.blackhornetworkshop.flowrush.initialization.SavedGame;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class AndroidLauncher extends AndroidApplication {

    //LibGDX classes
    FlowRush flowRush;
    AsyncExecutor asyncExecutor;

    //Utils
    FRPlayServices playServices;

    // Request code used to invoke sign in user interactions.
    private static final int RC_SIGN_IN = 9001;
    // Request code for listing saved games
    private static final int RC_LIST_SAVED_GAMES = 9002;
    // Request code for selecting a snapshot
    private static final int RC_SELECT_SNAPSHOT = 9003;
    // Request code for saving the game to a snapshot.
    private static final int RC_SAVE_SNAPSHOT = 9004;
    private static final int RC_LOAD_SNAPSHOT = 9005;

    private static final String TAG = "FlowRush";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        FRLogger.logDebug("AndroidLauncher onCreate() method");
        GoogleApiAvailability.getInstance();
        //AndroidLauncher initialization
        super.onCreate(savedInstanceState);
        AndroidSide androidSide = new AndroidSideConcrete(this);

        asyncExecutor = new AsyncExecutor(20);

        //FlowRushPlayServices initialization
        playServices = new FRPlayServices(this, asyncExecutor);


        //Game initialization
        try {
            FRLogger.logDebug("FlowRush initialization");
            FlowRush.initialize(androidSide, playServices, asyncExecutor);
            flowRush = FlowRush.getInstance();
            FRLogger.logDebug("FlowRush initialization is successful");
        }catch (FlowRushInitializeException ex) {
            FRLogger.logError("Initialization error", ex);
        }


        initialize(flowRush, getConfig());
    }

    private AndroidApplicationConfiguration getConfig() {
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        return config;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //playServices.gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //playServices.gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) { // CALLED WHEN USER SELECT A SNASHOT
        FRLogger.logDebug("RequestCode: "+requestCode);
        FRLogger.logDebug("ResultCode: "+resultCode);
        super.onActivityResult(requestCode, resultCode, intent);

/*        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                playServices.googleSignedInAccount = result.getSignInAccount();
                playServices.onConnected(playServices.googleSignedInAccount);
                FRLogger.logDebug("Sign in success");
            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }else*/ if (requestCode == RC_LOAD_SNAPSHOT) {
            FRLogger.logDebug("Loading snapshot");
        }

        /*if (intent != null) {
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
        //playServices.gameHelper.onActivityResult(requestCode, resultCode, intent);*/
    }




    public byte[] saveToGsonToBytes(SavedGame save){
        return flowRush.gson.toJson(save).getBytes();
    }


    //NEW METHOD IN 1.04 !!!!!
    public boolean isOnline() {
        final NetworkInfo activeNetworkInfo = ((ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {

            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SEPARATE THIS
            /*if (this.gameHelper.getApiClient().isConnected()) {
                System.out.println("playservices connected and internet is on");
                return true;
            }
            System.out.println("playservices not connected, internet is on");
            this.gameHelper.getApiClient().connect();*/
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SEPARATE THIS
        }
        System.out.println("internet is off");
        return false;
    }
    //NEW METHOD IN 1.04 !!!!
}