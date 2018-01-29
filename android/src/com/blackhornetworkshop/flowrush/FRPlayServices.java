package com.blackhornetworkshop.flowrush;

//Created by TScissors.

import android.content.Intent;
import android.support.annotation.NonNull;

import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.SnapshotsClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import static android.media.AudioTrack.SUCCESS;

public class FRPlayServices implements PlayServices {

    AndroidLauncher androidLauncher;
    FlowRush flowRush;
    AsyncExecutor asyncExecutor;

    GoogleApiAvailability googleApiAvailability;
    GoogleSignInClient googleSignInClient;
    GoogleSignInAccount googleSignedInAccount;

    private SnapshotsClient snapshotsClient;


    // Request code used to invoke sign in user interactions.
    private static final int RC_SIGN_IN = 9001;
    // Request code for listing saved games
    private static final int RC_LIST_SAVED_GAMES = 9002;
    // Request code for selecting a snapshot
    private static final int RC_SELECT_SNAPSHOT = 9003;
    // Request code for saving the game to a snapshot.
    private static final int RC_SAVE_SNAPSHOT = 9004;
    private static final int RC_LOAD_SNAPSHOT = 9005;

    private static final int RC_ACHIEVEMENT_UI = 9003;

    public FRPlayServices(AndroidLauncher androidLauncher, AsyncExecutor asyncExecutor) {
        this.androidLauncher = androidLauncher;
        this.asyncExecutor = asyncExecutor;
        googleApiAvailability = GoogleApiAvailability.getInstance();
        googleSignInClient = GoogleSignIn.getClient(androidLauncher,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                        // Since we are using SavedGames, we need to add the SCOPE_APPFOLDER to access Google Drive.
                        .requestScopes(Drive.SCOPE_APPFOLDER)
                        .build());
    }

    @Override
    public void signIn() {
        int result = googleApiAvailability.isGooglePlayServicesAvailable(androidLauncher.getContext());
        if (result == SUCCESS) {
            FRLogger.logDebug("Play Services are available, started sign in");
            googleSignInClient.silentSignIn().addOnCompleteListener(androidLauncher, new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                    if (task.isSuccessful()) {
                        FRLogger.logDebug("signInSilently(): success");
                        googleSignedInAccount = task.getResult();
                        snapshotsClient = Games.getSnapshotsClient(androidLauncher, googleSignedInAccount);
                    } else {
                        logError("signInSilently(): failure", task.getException());
                    }
                }
            });
        } else {
            FRLogger.logDebug("Play Services are not available");
        }
    }

    @Override
    public boolean isSignedIn() {
        return snapshotsClient != null;
    }

    @Override
    public void showSavedSnapshots() {
        //if(snapshotsClient == null) snapshotsClient = Games.getSnapshotsClient(androidLauncher, googleSignedInAccount);
        snapshotsClient.getSelectSnapshotIntent("Flow Rush saved games", false, true, 5).addOnCompleteListener(new OnCompleteListener<android.content.Intent>() {
            @Override
            public void onComplete(@NonNull Task<android.content.Intent> task) {
                if (!task.isSuccessful()) {
                    logError("Problem with loading snapshots list", task.getException());
                } else {
                    androidLauncher.startActivityForResult(task.getResult(), RC_LIST_SAVED_GAMES);
                }

            }
        });

    }


    @Override
    public void showAchievements() {
        Games.getAchievementsClient(androidLauncher, googleSignedInAccount)
                .getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        androidLauncher.startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                    }
                });
    }

    @Override
    public void signOut() {

    }

    @Override
    public void unlockAchievement(int num) {

    }

    @Override
    public void writeSnapshotAsync(byte[] data) {

    }



    @Override
    public void checkAndSave(boolean onStart) {

    }

    @Override
    public void disconnectGameHelper() {

    }
    @Override
    public void logError(String msg, Throwable tr) {
        FRLogger.logError(msg, tr);
    }

    @Override
    public void logDebug(String msg) {
        FRLogger.logDebug(msg);
    }
}
