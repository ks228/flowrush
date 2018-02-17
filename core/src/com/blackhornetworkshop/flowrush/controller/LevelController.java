package com.blackhornetworkshop.flowrush.controller;

import com.blackhornetworkshop.flowrush.model.ActorInfo;
import com.blackhornetworkshop.flowrush.model.Packs;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

//Created by TScissors.

public class LevelController {

    private static Packs packs;
    private static Packs.LevelPack levelPack;
    private static Packs.Level level;
    private static ArrayList<ArrayList<ActorInfo>> actorList;
    private static int currentPack;
    private static int currentLevel;

    public static void setPacks(Packs packs){
        LevelController.packs = packs;
    }

    public static void setCurrentLevel(int currentPack, int currentLevel) {
        FlowRush.logDebug("LevelController.setCurrentLevel: pack-"+currentPack+" level-"+currentLevel);
        LevelController.currentPack = currentPack;
        LevelController.currentLevel = currentLevel;

        levelPack = packs.packsArray.get(currentPack - 1);
        level = levelPack.levels.get(currentLevel - 1);

        reloadActorList();
        saveProgress();
        updatePackProgress();
    }

    public static void nextLvl() {
        currentLevel++;
        level = levelPack.levels.get(currentLevel - 1);

        FlowRush.logDebug("LevelController.nextLevel: "+currentPack);

        reloadActorList();
        saveProgress();
        updatePackProgress();
    }

    public static void nextPack() {
        currentLevel = 1;
        currentPack++;
        levelPack = packs.packsArray.get(currentPack - 1);
        level = levelPack.levels.get(currentLevel - 1);

        FlowRush.logDebug("LevelController.nextPack: "+currentPack);

        reloadActorList();
        saveProgress();
    }

    private static void saveProgress() {
        FlowRush.logDebug("LevelController saveProgress() method called");
        FlowRush.getSave().setCurrentPack(getCurrentPack());
        FlowRush.getSave().setCurrentLvl(getCurrentLevel());
    }

    public static boolean nextLevelExist() {
        return levelPack.levels.size() > currentLevel;
    }

    public static boolean nextPackExist(){ return currentPack < 4;}

    public static ArrayList<ArrayList<ActorInfo>> getActorList() {
        return actorList;
    }

    public static void reloadActorList() {
        FlowRush.logDebug("LevelController reloadActorList() method called");
        actorList = FlowRush.getGson().fromJson(level.actorListJson, new TypeToken<ArrayList<ArrayList<ActorInfo>>>() {}.getType());
    }

    public static int getCurrentPack() {
        return currentPack;
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    private static void updatePackProgress() {
        if (FlowRush.getSave().getLevelsProgress(currentPack -1) < FlowRush.getSave().getCurrentLvl()) {
            FlowRush.getSave().setLevelsProgress(currentPack - 1, getCurrentLevel());
        }
    }

    public static void prevLvl() {
        currentLevel--;
        level = levelPack.levels.get(currentLevel - 1);
        reloadActorList();
        saveProgress();
        FlowRush.logDebug("LevelController.prevLvl: "+currentLevel);
    }

    public static Packs.LevelPack getLevelPack(int x) {
        return packs.packsArray.get(x);
    }

    public static String getPackName(int pack){
        switch (pack) {
            case 1:
                return "QUESTION";
            case 2:
                return "IDEA";
            case 3:
                return "MISSION";
            case 4:
                return "FLYING";
            case 5:
                return "SOURCE";
            default:
                return "";
        }
    }
}