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
    
    //DELETED !!!!!!!!!!!!!!!!!!!!!!!! 
    //void disposeAsyncExecutor(); //DELETED !!!!!!!!!!!!!!!!!!!!!!!
    
    //ADDED IN 1.04 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    void disconnectGameHelper();
}
