package com.blackhornetworkshop.flowrush.model;

//Created by TScissors.

public interface PlayServices {
    void signIn();
    void signOut();
    boolean isSignedIn();

    void saveGame();
    void showSavedSnapshots();

    void showAchievements();
    void unlockAchievement(int num);
}
