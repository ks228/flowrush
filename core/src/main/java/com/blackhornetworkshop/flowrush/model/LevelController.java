package com.blackhornetworkshop.flowrush.model;

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
        //System.out.println(pck+" currentPack");
        //System.out.println(lv+" currentLevel");
        LevelController.currentPack = currentPack;
        LevelController.currentLevel = currentLevel;

        levelPack = packs.packsArray.get(currentPack - 1);
        level = levelPack.levels.get(currentLevel - 1);

        reloadActorList();
        saveToPrefs();
        checkPackProgress();
    }

    public static void nextLvl() {
        currentLevel++;
        //System.out.println("next "+currentPack+"-currentPack, "+currentLevel+"-currentLevel.");
        level = levelPack.levels.get(currentLevel - 1);


        reloadActorList();
        saveToPrefs();
        checkPackProgress();
    }

    public static void nextPack() {
        currentLevel = 1;
        currentPack++;
        //System.out.println("next "+currentPack+"-currentPack, "+currentLevel+"-currentLevel.");
        levelPack = packs.packsArray.get(currentPack - 1);
        level = levelPack.levels.get(currentLevel - 1);

        reloadActorList();
        saveToPrefs();
    }

    private static void saveToPrefs() {
        FlowRush.getSave().setCurrentPack(getPack());
        FlowRush.getSave().setCurrentLvl(getCurrentLevel());
    }

    public static boolean containsNext() {
        return levelPack.levels.size() > currentLevel;
    }

    public static ArrayList<ArrayList<ActorInfo>> getActorList() {
        return actorList;
    }

    public static void reloadActorList() { //startNewLevel если отдельно взять метод
        actorList = FlowRush.getGson().fromJson(level.actorListJson, new TypeToken<ArrayList<ArrayList<ActorInfo>>>() {
        }.getType());
    }

    public static int getPack() {
        return currentPack;
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    private static void checkPackProgress() {
        if (FlowRush.getSave().getLevelsProgress(currentPack -1) < FlowRush.getSave().getCurrentLvl()) { //здесь мы обновляем прогресс пака
            FlowRush.getSave().setLevelsProgress(currentPack - 1, getCurrentLevel());
        }
    }

    public static void prevLvl() {
        currentLevel--;
        level = levelPack.levels.get(currentLevel - 1);
        reloadActorList();
        saveToPrefs();
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
                return "SOURCE\ncoming soon";
            default:
                return "";
        }
    }
}