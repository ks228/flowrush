package com.blackhornetworkshop.flowrush.model;

//Created by TScissors.

public class SavedGame {

    private String uniqSaveGameName;
    private boolean[] achievements = {false, false, false, false, false, false, false, false, false, false};
    private int currentLvl = 1;
    private int currentPack = 1;
    private int[] levelsProgress = {1, 1, 1, 1, 5};
    private boolean[] finishedPacks = {false, false, false, false, false};


    public void setUniqSaveName() {//Вызывать только когда создатся новый объект GamePreferences
        uniqSaveGameName = Long.toString(System.currentTimeMillis());
    }

    public String getUniqSnapshotName() {
        return uniqSaveGameName;
    }

    public boolean getAchievements(int num) {
        return achievements[num];
    }

    public void unlockAchievement(int num) { achievements[num] = true; }

    public int getCurrentLvl() {
        return currentLvl;
    }

    public void setCurrentLvl(int lvl) {
        currentLvl = lvl;
    }

    public int getCurrentPack() {
        return currentPack;
    }

    public void setCurrentPack(int pack) {
        currentPack = pack;
    }

    public int getLevelsProgress(int pack) {
        return levelsProgress[pack];
    }

    public void finishPack(int num) {
        finishedPacks[num] = true;
    }

    public boolean isPackFinished(int num){
        return finishedPacks[num];
    }

    public void setLevelsProgress(int pack, int value) {
        levelsProgress[pack] = value;
    }
}
