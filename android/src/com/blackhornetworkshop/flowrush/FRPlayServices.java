package com.blackhornetworkshop.flowrush;

//Created by TScissors.

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.blackhornetworkshop.flowrush.model.PlayServices;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.model.FRConstants;
import com.blackhornetworkshop.flowrush.model.SavedGame;
import com.blackhornetworkshop.flowrush.model.ex.FlowRushException;
import com.blackhornetworkshop.flowrush.model.FlowRush;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.AchievementsClient;
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
import com.google.android.gms.tasks.TaskCompletionSource;

import java.io.IOException;
import java.util.Calendar;

import static com.google.android.gms.common.api.CommonStatusCodes.SUCCESS;

public class FRPlayServices implements PlayServices {

    private static volatile FRPlayServices instance;

    private AndroidLauncher app;
    private GoogleSignInClient googleSignInClient;

    private GoogleSignInAccount googleAccount;
    private SnapshotsClient snapshotsClient;
    private AchievementsClient achievementsClient;

    private FRPlayServices(){}

    static boolean isPlayServicesAvailable(Context context) {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == SUCCESS;
    }

    static FRPlayServices getInstance() {
        if (instance == null) {
            synchronized (FRPlayServices.class) {
                if (instance == null) {
                    instance = new FRPlayServices();
                }
            }
        }
        return instance;
    }

    void setup(AndroidLauncher app) {
        this.app = app;
        googleSignInClient = GoogleSignIn.getClient(app,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                        .requestScopes(Drive.SCOPE_APPFOLDER)
                        .build());
        if (isSignedIn()) {
            googleAccount = GoogleSignIn.getLastSignedInAccount(app);
            snapshotsClient = Games.getSnapshotsClient(app, googleAccount);
            achievementsClient = Games.getAchievementsClient(app, googleAccount);
            FRAndroidHelper.getInstance().logDebug("User signed. Google account, snapshot client, achievements client are received");
        }
    }

    @Override
    public void signIn() {
        FRAndroidHelper.getInstance().logDebug("FRPlayServices signIn() method called");
        app.startActivityForResult(googleSignInClient.getSignInIntent(), FRConstants.RC_SIGN_IN);
    }

    void handleSignInResult(Intent intent) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);

        try {
            googleAccount = task.getResult(ApiException.class);
            FRAndroidHelper.getInstance().logDebug("Google sign in success");
            onConnected();
        } catch (ApiException apiException) {
            FRAndroidHelper.getInstance().logError("Google sign in failed", apiException);
        }
    }

    @Override
    public void signOut() {
        FRAndroidHelper.getInstance().logDebug("FRPlayServices signOut() method called");
        googleSignInClient.signOut().addOnCompleteListener(app,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FRAndroidHelper.getInstance().logDebug("Google sign out success");
                        } else {
                            FRAndroidHelper.getInstance().logError("Google sign out failure", task.getException());
                        }
                        onDisconnected();
                    }
                });
    }

    @Override
    public boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(app) != null;
    }

    @Override
    public void showSavedSnapshots() {
        FRAndroidHelper.getInstance().logDebug("FRPlayServices showSavedSnapshots() method called");
        if (snapshotsClient == null)
            snapshotsClient = Games.getSnapshotsClient(app, googleAccount);
        snapshotsClient.getSelectSnapshotIntent("Flow Rush saved games", true, true, 5).addOnCompleteListener(new OnCompleteListener<Intent>() {
            @Override
            public void onComplete(@NonNull Task<Intent> task) {
                if (!task.isSuccessful()) {
                    FRAndroidHelper.getInstance().logError("Problem with loading snapshots list", task.getException());
                } else {
                    app.startActivityForResult(task.getResult(), FRConstants.RC_LIST_SAVED_GAMES);
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
                FRAndroidHelper.getInstance().logDebug("User chooses to save a new game");
                FlowRush.getSave().setUniqSaveName();
                saveGame();
            }
        }
    }

    @Override
    public void saveGame() {
        snapshotsClient.open(FlowRush.getSave().getUniqSnapshotName(), true)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FRAndroidHelper.getInstance().logError("Error while opening Snapshot for save.", e);
                    }
                }).continueWith(new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, byte[]>() {
            @Override
            public byte[] then(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) throws Exception {
                Snapshot snapshot;

                if (!task.getResult().isConflict()) {
                    FRAndroidHelper.getInstance().logDebug("No save snapshot conflicts");
                    snapshot = task.getResult().getData();
                    // Set the data payload for the snapshot
                    snapshot.getSnapshotContents().writeBytes(FlowRush.getGson().toJson(FlowRush.getSave()).getBytes());
                    writeSnapshot(snapshot);
                }else{
                    FRAndroidHelper.getInstance().logDebug("Save snapshot conflict, start resolving...");
                    snapshot = processSnapshotOpenResult(task.getResult(), FRConstants.MAX_SNAPSHOT_RESOLVE_RETRIES).getResult();

                    FRAndroidHelper.getInstance().logDebug("Opening the snapshot was a success and any conflicts have been resolved.");
                    if(snapshot != null){
                        writeSnapshot(snapshot);
                    }else {
                        FRAndroidHelper.getInstance().logError("Error with saving snapshot", new NullPointerException());
                    }
                }
                return null;
            }
        });
    }

    private void writeSnapshot(Snapshot snapshot) throws IOException{
        FRAndroidHelper.getInstance().logDebug("Save snapshot: "+new String(snapshot.getSnapshotContents().readFully()));

        // Save the snapshot.
        SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder()
                .setDescription(Calendar.getInstance().getTime().toString())
                .build();

        snapshotsClient.commitAndClose(snapshot, metadataChange);
    }

    private Task<Snapshot> processSnapshotOpenResult(SnapshotsClient.DataOrConflict<Snapshot> result, final int retryCount) throws IOException {
        if (!result.isConflict()) {
            FRAndroidHelper.getInstance().logDebug("No save snapshot conflicts, return open result");
            TaskCompletionSource<Snapshot> source = new TaskCompletionSource<Snapshot>();
            source.setResult(result.getData());
            return source.getTask();
        }

        FRAndroidHelper.getInstance().logDebug("Start resolving snapshot conflict...");

        SnapshotsClient.SnapshotConflict conflict = result.getConflict();

        Snapshot resolvedSnapshot = resolveConflict(conflict.getSnapshot(), conflict.getConflictingSnapshot());

        return snapshotsClient.resolveConflict(conflict.getConflictId(), resolvedSnapshot)
                .continueWithTask(
                        new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, Task<Snapshot>>() {
                            @Override
                            public Task<Snapshot> then(
                                    @NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task)
                                    throws IOException {
                                // Resolving the conflict may cause another conflict, so recurse and try another resolution.
                                if (retryCount < FRConstants.MAX_SNAPSHOT_RESOLVE_RETRIES) {
                                    return processSnapshotOpenResult(task.getResult(), retryCount + 1);
                                } else {
                                    FRAndroidHelper.getInstance().logError("Could not resolve snapshot conflicts", new Exception());
                                    return null;
                                }
                            }
                        });
    }

    private Snapshot resolveConflict(Snapshot snapshot, Snapshot conflictSnapshot) throws IOException {
        FRAndroidHelper.getInstance().logDebug("Resolve conflict snapshot");
        String serverJson = new String(snapshot.getSnapshotContents().readFully());
        SavedGame serverSavedGame = FlowRush.getGson().fromJson(serverJson, SavedGame.class);

        String conflictJson = new String(conflictSnapshot.getSnapshotContents().readFully());
        SavedGame conflictSavedGame = FlowRush.getGson().fromJson(conflictJson, SavedGame.class);

        boolean isCurrentLevelActual = true;

        //check level progress
        for (int pack = 0; pack < 5; pack++) {
            if (conflictSavedGame.getLevelsProgress(pack)< serverSavedGame.getLevelsProgress(pack)) {
                conflictSavedGame.setLevelsProgress(pack, serverSavedGame.getLevelsProgress(pack));
                FRAndroidHelper.getInstance().logDebug("Updating level progress in pack " + pack + " in conflict save");
                isCurrentLevelActual = false;
            } else {
                FRAndroidHelper.getInstance().logDebug("Level progress of pack " + pack + " is actual");
            }
        }

        //check the relevance of the current level
        if (isCurrentLevelActual) {
            FRAndroidHelper.getInstance().logDebug("Updating current in conflict save");
            conflictSavedGame.setCurrentLvl(serverSavedGame.getCurrentLvl());
            conflictSavedGame.setCurrentPack(serverSavedGame.getCurrentPack());
        } else {
            FRAndroidHelper.getInstance().logDebug("Current level is actual");
        }

        //commit changes
        conflictSnapshot.getSnapshotContents().writeBytes(FlowRush.getGson().toJson(conflictSavedGame).getBytes());

        return conflictSnapshot;
    }

    private void loadSnapshot(final SnapshotMetadata snapshotMetadata) {
        app.showLoadingLabel();
        FlowRush.getInstance().pause();

        FRAndroidHelper.getInstance().logDebug("Open the saved game");
        snapshotsClient.open(snapshotMetadata)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FRAndroidHelper.getInstance().logError("Error while opening Snapshot for load.", e);
                    }
                }).continueWith(new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, byte[]>() {
            @Override
            public byte[] then(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) throws IOException {
                Snapshot snapshot = processSnapshotOpenResult(task.getResult(), FRConstants.MAX_SNAPSHOT_RESOLVE_RETRIES).getResult();
                FRAndroidHelper.getInstance().logDebug("Opening the snapshot was a success and any conflicts have been resolved.");
                try {
                    FRAndroidHelper.getInstance().logDebug("Extract the raw data from the snapshot.");
                    return snapshot.getSnapshotContents().readFully();
                } catch (Exception e) {
                    FRAndroidHelper.getInstance().logError("Error while reading Snapshot.", e);
                }

                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                FRAndroidHelper.getInstance().logDebug("Downloading a snapshot from the server is complete");
                String snapshotInJson = new String(task.getResult());
                FRAndroidHelper.getInstance().logDebug("Downloaded snapshot: "+snapshotInJson);
                FlowRush.setSave(FlowRush.getGson().fromJson(snapshotInJson, SavedGame.class));
                FRAndroidHelper.getInstance().logDebug("Snapshot reading from json is completed successfully");
                app.hideLoadingDialog();
                ScreenManager.setMenuMainScreen();
            }
        });
    }

    @Override
    public void showAchievements() {
        FRAndroidHelper.getInstance().logDebug("FRPlayServices showAchievements() method called");
        achievementsClient.getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        app.startActivityForResult(intent, FRConstants.RC_ACHIEVEMENT_UI);
                    }
                });
    }

    @Override
    public void unlockAchievement(int num) {
        FRAndroidHelper.getInstance().logDebug("Unlock Play Games achievement: " + num);
        String s;
        switch (num) {
            case 1:
                s = app.getString(R.string.achievement_a_good_start);
                break;
            case 2:
                s = app.getString(R.string.achievement_dove);
                break;
            case 3:
                s = app.getString(R.string.achievement_question_answered);
                break;
            case 4:
                s = app.getString(R.string.achievement_great_idea);
                break;
            case 5:
                s = app.getString(R.string.achievement_mission_possible);
                break;
            case 6:
                s = app.getString(R.string.achievement_flying);
                break;
            case 7:
                s = app.getString(R.string.achievement_source_of_knowledge_is_found);
                break;
            case 8:
                s = app.getString(R.string.achievement_the_end);
                break;
            default:
                throw new FlowRushException("No such achievement!");
        }
        achievementsClient.unlock(s);
    }

    private void onConnected() {
        snapshotsClient = Games.getSnapshotsClient(app, googleAccount);
        achievementsClient = Games.getAchievementsClient(app, googleAccount);
        FRAndroidHelper.getInstance().logDebug("SnapshotСlient received successfully");
        saveGame();
        FlowRush.checkAchievements();
    }

    private void onDisconnected() {
        FRAndroidHelper.getInstance().logDebug("Snapshot client is disconnected");
        snapshotsClient = null;
    }

}
