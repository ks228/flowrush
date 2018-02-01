package com.blackhornetworkshop.flowrush;

//Created by TScissors.

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.blackhornetworkshop.flowrush.ex.FlowRushInitializeException;
import com.blackhornetworkshop.flowrush.initialization.SavedGame;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.SnapshotsClient;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Calendar;

import static com.google.android.gms.common.api.CommonStatusCodes.SUCCESS;

public class FRPlayServices implements PlayServices {

    private static FRPlayServices playServices;

    private AndroidLauncher androidLauncher;
    private GoogleSignInClient googleSignInClient;

    private GoogleSignInAccount googleAccount;
    private SnapshotsClient snapshotsClient;

    static final int RC_SIGN_IN = 9001;
    static final int RC_LIST_SAVED_GAMES = 9002;
    private static final int RC_ACHIEVEMENT_UI = 9003;

    final static int conflictResolutionPolicy = SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED;

    private FRPlayServices(AndroidLauncher androidLauncher) {
        this.androidLauncher = androidLauncher;
        googleSignInClient = GoogleSignIn.getClient(androidLauncher,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                        .requestScopes(Drive.SCOPE_APPFOLDER)
                        .build());
        if(isSignedIn()){
            googleAccount = GoogleSignIn.getLastSignedInAccount(androidLauncher);
            onAccountChanged(googleAccount);
        }
    }

    static void initialize(AndroidLauncher androidLauncher){
        if(playServices == null) {
            playServices = new FRPlayServices(androidLauncher);
            FRLogger.logDebug("Play Services are initialized");
        }else {
            throw new FlowRushInitializeException("FRPlayServices is already initialized!");
        }
    }

    static FRPlayServices getInstance(){
        if(playServices != null){
            return playServices;
        }else{
            throw new FlowRushInitializeException("FRPlayServices is not initialized!");
        }
    }

    static boolean isPlayServicesAvailable(Context context){
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == SUCCESS;
    }

    @Override
    public void signIn() {
        androidLauncher.startActivityForResult(googleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    public void handleSignInResult(Intent intent){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            playServices.onConnected(account);
        } catch (ApiException apiException) {
            FRLogger.logError("Sign in error", apiException);
            androidLauncher.handleException(apiException, "Sign in error");
        }
    }

    @Override
    public void signOut() {
        FRLogger.logDebug("Sign out started");
        googleSignInClient.signOut().addOnCompleteListener(androidLauncher,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FRLogger.logDebug("Sign out success");
                        } else {
                            FRLogger.logError("Sign out failure", task.getException());
                            androidLauncher.handleException(task.getException(), "Sign out failed!");
                        }
                        onDisconnected();
                    }
                });
    }

    @Override
    public boolean isSignedIn(){
        return GoogleSignIn.getLastSignedInAccount(androidLauncher) != null;
    }

    @Override
    public void showSavedSnapshots() {
        if(snapshotsClient == null) snapshotsClient = Games.getSnapshotsClient(androidLauncher, googleAccount);
        snapshotsClient.getSelectSnapshotIntent("Flow Rush saved games", true, true, 5).addOnCompleteListener(new OnCompleteListener<Intent>() {
            @Override
            public void onComplete(@NonNull Task<Intent> task) {
                if (!task.isSuccessful()) {
                    FRLogger.logError("Problem with loading snapshots list", task.getException());
                } else {
                    androidLauncher.startActivityForResult(task.getResult(), RC_LIST_SAVED_GAMES);
                }
            }
        });
    }


    void handleSnapshotSelectResult(Intent intent) {
        // the standard snapshot selection intent
        if (intent != null) {
            if (intent.hasExtra(SnapshotsClient.EXTRA_SNAPSHOT_METADATA)) {
                // Load a snapshot.
                SnapshotMetadata snapshotMetadata = intent.getParcelableExtra(SnapshotsClient.EXTRA_SNAPSHOT_METADATA);
                loadSnapshot(snapshotMetadata);
            } else if (intent.hasExtra(SnapshotsClient.EXTRA_SNAPSHOT_NEW)) {
                // Create a new snapshot named with a unique string
                FRLogger.logDebug("User select: save new game");
                androidLauncher.flowRush.save.setUniqSaveName();
                saveGame();
            }
        }
    }

    @Override
    public void saveGame() {
        String snapshotName = androidLauncher.flowRush.save.getUniqSnapshotName();
        FRLogger.logDebug("Writing data to snapshot: " + snapshotName);
        writeSnapshot(snapshotName)
                .addOnCompleteListener(new OnCompleteListener<SnapshotsClient.DataOrConflict<Snapshot>>() {
                    @Override
                    public void onComplete(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) {
                        if (task.isSuccessful()) {
                            FRLogger.logDebug("Snapshot saved!");
                        } else {
                            androidLauncher.handleException(task.getException(), androidLauncher.getString(R.string.write_snapshot_error));
                        }
                    }
                });
    }

    private Task<SnapshotsClient.DataOrConflict<Snapshot>> writeSnapshot(String snapshotName) {
        return snapshotsClient.open(snapshotName, true, conflictResolutionPolicy)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FRLogger.logError("Error while opening Snapshot.", e);
                    }
                }).addOnCompleteListener(new OnCompleteListener<SnapshotsClient.DataOrConflict<Snapshot>>() {
            @Override
            public void onComplete(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) {
                // Set the data payload for the snapshot.
                Snapshot snapshot = task.getResult().getData();
                snapshot.getSnapshotContents().writeBytes(androidLauncher.flowRush.getSaveData());

                // Save the snapshot.
                SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder()
                        .setDescription(Calendar.getInstance().getTime().toString())
                        .build();

                snapshotsClient.commitAndClose(snapshot, metadataChange);
            }
        });
    }

    void loadSnapshot(final SnapshotMetadata snapshotMetadata) {
        androidLauncher.showLoadingDialog("Loading...");
        androidLauncher.flowRush.pause();

        FRLogger.logDebug("Open the saved game");
        snapshotsClient.open(snapshotMetadata, conflictResolutionPolicy)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FRLogger.logError("Error while opening Snapshot.", e);
                    }
                }).continueWith(new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, byte[]>() {
                    @Override
                    public byte[] then(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) throws Exception {
                        Snapshot snapshot = task.getResult().getData();
                        FRLogger.logDebug("Opening the snapshot was a success and any conflicts have been resolved.");
                        try {
                            FRLogger.logDebug("Extract the raw data from the snapshot.");
                            return snapshot.getSnapshotContents().readFully();
                        } catch (IOException e) {
                            FRLogger.logError("Error while reading Snapshot.", e);
                        }

                        return null;
                    }
                }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                    @Override
                    public void onComplete(@NonNull Task<byte[]> task) {
                        FRLogger.logDebug("Downloading a snapshot from the server is complete");
                        String snapshotInJson = new String(task.getResult());
                        androidLauncher.flowRush.save = androidLauncher.flowRush.gson.fromJson(snapshotInJson, SavedGame.class);
                        FRLogger.logDebug("Snapshot reading from json is completed successfully");
                        androidLauncher.hideLoadingDialog();
                        androidLauncher.flowRush.resume();
                    }
                });
    }

    @Override
    public void showAchievements() {
        Games.getAchievementsClient(androidLauncher, googleAccount)
                .getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        androidLauncher.startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                    }
                });
    }

    @Override
    public void unlockAchievement(int num) {
    }

    private void onConnected(GoogleSignInAccount googleSignInAccount) {
        FRLogger.logDebug("Connected to Google APIs");
        if (googleAccount != googleSignInAccount) {
            googleAccount = googleSignInAccount;

            onAccountChanged(googleSignInAccount);
        }
    }

    private void onAccountChanged(GoogleSignInAccount googleSignInAccount) {
        snapshotsClient = Games.getSnapshotsClient(androidLauncher, googleSignInAccount);
        FRLogger.logDebug("SnapshotСlient received successfully");
    }

    private void onDisconnected() {
        FRLogger.logDebug("Snapshot client is disconnected");
        snapshotsClient = null;
    }

}
