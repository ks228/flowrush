package com.blackhornetworkshop.flowrush;

//Created by TScissors.

public interface PlayServices
{
    void signIn();
    void signOut();
    void showSavedSnapshots();
    void showAchievements();

    void unlockAchievement(int num);
    void writeSnapshotAsync(byte[] data);
    boolean isSignedIn();
    void checkAndSave(boolean onStart);
    void disconnectGameHelper();

    void logError(String msg, Throwable tr);
    void logDebug(String msg);
}
