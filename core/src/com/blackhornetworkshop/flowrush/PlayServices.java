package com.blackhornetworkshop.flowrush;

//Created by TScissors.

public interface PlayServices
{
    void signIn();
    void signOut();
    void unlockAchievement(int num);
    void writeSnapshotAsync(byte[] data);
    boolean isSignedIn();
    void checkAndSave(boolean onStart);
    void disposeAsyncExecutor();
}
