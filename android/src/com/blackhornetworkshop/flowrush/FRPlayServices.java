package com.blackhornetworkshop.flowrush;

//Created by TScissors.

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.model.PlayServices;
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

import java.io.IOException;
import java.util.Calendar;

import static com.google.android.gms.common.api.CommonStatusCodes.SUCCESS;
import static com.google.android.gms.games.SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED;

public class FRPlayServices implements PlayServices {

    private static volatile FRPlayServices instance;

    private AndroidLauncher app;
    private GoogleSignInClient googleSignInClient;

    private SnapshotsClient snapshotsClient;
    private AchievementsClient achievementsClient;

    private FRPlayServices() {
    }

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
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(app);
        if (googleAccount != null && app.isPlayGamesPackageInstalled()) {
            if(app.getCurrentFocus() != null) {
                Games.getGamesClient(app, googleAccount).setViewForPopups(app.getCurrentFocus());
            }
            snapshotsClient = Games.getSnapshotsClient(app, googleAccount);
            achievementsClient = Games.getAchievementsClient(app, googleAccount);
            FRAndroidHelper.getInstance().logDebug("User signed. Google account, games client, snapshot client, achievements client are received");
        }
    }

    @Override
    public void signIn() {
        FRAndroidHelper.getInstance().logDebug("FRPlayServices signIn() method called");
        app.startActivityForResult(googleSignInClient.getSignInIntent(), FRConstants.RC_SIGN_IN);
    }

    void handleSignInResult(Intent intent) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);

            task.getResult(ApiException.class);
            FRAndroidHelper.getInstance().logDebug("Google sign in success");
            onConnected(GoogleSignIn.getLastSignedInAccount(app));
        } catch (Exception e) {
            FRAndroidHelper.getInstance().showToast("Google sign in failure");
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
        return GoogleSignIn.getLastSignedInAccount(app) != null && app.isPlayGamesPackageInstalled();
    }

    @Override
    public void showSavedSnapshots() {
        FRAndroidHelper.getInstance().logDebug("FRPlayServices showSavedSnapshots() method called");
        snapshotsClient.getSelectSnapshotIntent("Flow Rush saved games", true, true, 5).addOnCompleteListener(new OnCompleteListener<Intent>() {
            @Override
            public void onComplete(@NonNull Task<Intent> task) {
                if (!task.isSuccessful()) {
                    FRAndroidHelper.getInstance().logError("Problem with loading saved games list", task.getException());
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
        // In the case of a conflict, the most recently modified version of this snapshot will be used.
        // Open the saved game using its name.
        snapshotsClient.open(FlowRush.getSave().getUniqSnapshotName(), true, RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FRAndroidHelper.getInstance().logError("Error while saving in Google Play Games", e);
                    }
                }).continueWith(new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, byte[]>() {
            @Override
            public byte[] then(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) throws Exception {
                try {
                    Snapshot snapshot = task.getResult().getData();
                    // Opening the snapshot was a success and any conflicts have been resolved.

                    byte[] data = snapshot.getSnapshotContents().readFully();
                    if (data != null && data.length > 0) {
                        // Extract the raw data from the snapshot.
                        FRAndroidHelper.getInstance().logDebug("Extracting the raw data from the snapshot is completed");
                        String snapshotInJson = new String(data);
                        FRAndroidHelper.getInstance().logDebug("Downloaded snapshot data: " + snapshotInJson);

                        // preventing downgrade of level progress in case when
                        // playing on different devices with the same snapshot name
                        SavedGame serverSavedGame = FlowRush.getGson().fromJson(snapshotInJson, SavedGame.class);
                        SavedGame updatedSavedGame = updateSavedGame(serverSavedGame, FlowRush.getSave());

                        // Update raw data
                        snapshot.getSnapshotContents().writeBytes(FlowRush.getGson().toJson(updatedSavedGame).getBytes());
                    } else {
                        FRAndroidHelper.getInstance().logDebug("Raw data in snapshot is empty or null");
                        // Write raw data
                        snapshot.getSnapshotContents().writeBytes(FlowRush.getGson().toJson(FlowRush.getSave()).getBytes());
                    }
                    // Write updated snapshot
                    writeSnapshot(snapshot);
                } catch (Exception e) {
                    FRAndroidHelper.getInstance().logError("Error while saving in Google Play Games", e);
                }

                return null;
            }
        });
    }

    private void loadSnapshot(final SnapshotMetadata snapshotMetadata) {
        app.showLoadingDialog();
        FlowRush.getInstance().pause();

        FRAndroidHelper.getInstance().logDebug("Open the saved game");
        snapshotsClient.open(snapshotMetadata, RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FRAndroidHelper.getInstance().logError("Error while loading saved game from Google Play Games", e);
                    }
                }).continueWith(new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, byte[]>() {
            @Override
            public byte[] then(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) throws Exception {
                try {
                    Snapshot snapshot = task.getResult().getData();
                    FRAndroidHelper.getInstance().logDebug("Opening the snapshot was a success and any conflicts have been resolved.");

                    FRAndroidHelper.getInstance().logDebug("Extract the raw data from the snapshot.");
                    String snapshotInJson = new String(snapshot.getSnapshotContents().readFully());
                    FRAndroidHelper.getInstance().logDebug("Downloaded snapshot data: " + snapshotInJson);

                    FRAndroidHelper.getInstance().logDebug("Server snapshot name: "+snapshot.getMetadata().getUniqueName());
                    FRAndroidHelper.getInstance().logDebug("SavedGame name: "+FlowRush.getSave().getUniqSnapshotName());

                    if(snapshot.getMetadata().getUniqueName().equals(FlowRush.getSave().getUniqSnapshotName())) {
                        FRAndroidHelper.getInstance().logDebug("Updating current saved game");

                        // preventing downgrade of level progress in case when conflict is happened and
                        // was saved lower level progress (when other device was offline)
                        SavedGame serverSavedGame = FlowRush.getGson().fromJson(snapshotInJson, SavedGame.class);
                        SavedGame updatedSavedGame = updateSavedGame(FlowRush.getSave(), serverSavedGame);

                        FlowRush.setSave(updatedSavedGame);
                    }else{
                        FRAndroidHelper.getInstance().logDebug("Download new saved game");
                        FlowRush.setSave(FlowRush.getGson().fromJson(snapshotInJson, SavedGame.class));
                    }
                    FRAndroidHelper.getInstance().logDebug("Loading saved game from Google Play Games successful");
                } catch (Exception e) {
                    FRAndroidHelper.getInstance().logError("Error while loading saved game from Google Play Games.", e);
                }

                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                app.hideLoadingDialog();
                ScreenManager.setMenuMainScreen();
            }
        });
    }


    private SavedGame updateSavedGame(SavedGame updatingSavedGame, SavedGame newSavedGame) throws IOException {
        FRAndroidHelper.getInstance().logDebug("Updating saved game");

        boolean isCurrentLevelActual = true;

        //check level progress
        for (int pack = 0; pack < 5; pack++) {
            if (updatingSavedGame.getLevelsProgress(pack) < newSavedGame.getLevelsProgress(pack)) {
                updatingSavedGame.setLevelsProgress(pack, newSavedGame.getLevelsProgress(pack));
                FRAndroidHelper.getInstance().logDebug("Updating level progress in pack " + pack);
                isCurrentLevelActual = false;
            } else {
                FRAndroidHelper.getInstance().logDebug("Level progress of pack " + pack + " is actual");
            }
        }

        //check the relevance of the current level
        if (!isCurrentLevelActual) {
            FRAndroidHelper.getInstance().logDebug("Updating current level and pack");
            updatingSavedGame.setCurrentLvl(newSavedGame.getCurrentLvl());
            updatingSavedGame.setCurrentPack(newSavedGame.getCurrentPack());
        } else {
            FRAndroidHelper.getInstance().logDebug("Current level is actual");
        }

        return updatingSavedGame;
    }

    private void writeSnapshot(Snapshot snapshot) throws IOException {
        FRAndroidHelper.getInstance().logDebug("Save snapshot: " + new String(snapshot.getSnapshotContents().readFully()));

        // Save the snapshot.
        SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder()
                .setDescription(Calendar.getInstance().getTime().toString())
                .build();

        snapshotsClient.commitAndClose(snapshot, metadataChange);

        FRAndroidHelper.getInstance().logDebug("Saving in Google Play Games is successful");
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

    private void onConnected(GoogleSignInAccount googleAccount) {
        snapshotsClient = Games.getSnapshotsClient(app, googleAccount);
        if(app.getCurrentFocus() != null) {
            Games.getGamesClient(app, googleAccount).setViewForPopups(app.getCurrentFocus());
        }
        achievementsClient = Games.getAchievementsClient(app, googleAccount);
        FRAndroidHelper.getInstance().logDebug("SnapshotСlient received successfully");
        saveGame();
        FlowRush.checkAchievements();
    }

    private void onDisconnected() {
        FRAndroidHelper.getInstance().logDebug("Snapshot client is disconnected");
        snapshotsClient = null;
        achievementsClient = null;
    }

}
