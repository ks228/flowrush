package com.blackhornetworkshop.flowrush.controller;

import com.badlogic.gdx.Gdx;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.model.ActorInfo;
import com.blackhornetworkshop.flowrush.model.Packs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

//Created by TScissors. Класс для загрузки и смены actorList через pack и lvl

public class LevelLoader {

    private static LevelLoader instance;

    private Packs packs;
    private Packs.LevelPack levelPack;
    private Packs.Level level;
    private ArrayList<ArrayList<ActorInfo>> actorList;
    private int pack;
    private int lvl;

    public static LevelLoader getInstance(){
        if(instance == null){
            instance = new LevelLoader();
            FlowRush.logDebug("LevelLoader is initialized. Return new instance");
        }else{
            FlowRush.logDebug("LevelLoader is already initialized. Return existing instance");
        }
        return instance;
    }

    private LevelLoader() {
        try {
            packs = new Gson().fromJson(Gdx.files.internal("lvls/levels.json").reader(), Packs.class);
            //System.out.println("Packs loaded!");
        } catch (Exception ex) {
            //System.out.println("File levels.json not found!");

        }
    }

    public void setLvl(int pck, int lv) {
        //System.out.println(pck+" pack");
        //System.out.println(lv+" lvl");
        this.pack = pck;
        this.lvl = lv;

        levelPack = packs.packsArray.get(pack - 1);
        level = levelPack.levels.get(lvl - 1);

        reloadActorList();
        saveToPrefs();
        checkPackProgress();
    }

    public void nextLvl() {
        lvl++;
        //System.out.println("next "+pack+"-pack, "+lvl+"-lvl.");
        level = levelPack.levels.get(lvl - 1);


        reloadActorList();
        saveToPrefs();
        checkPackProgress();
    }

    public void nextPack() {
        lvl = 1;
        pack++;
        //System.out.println("next "+pack+"-pack, "+lvl+"-lvl.");
        levelPack = packs.packsArray.get(pack - 1);
        level = levelPack.levels.get(lvl - 1);

        reloadActorList();
        saveToPrefs();
    }

    private void saveToPrefs() {
        FlowRush.getInstance().save.setCurrentPack(getPack());
        FlowRush.getInstance().save.setCurrentLvl(getLvl());
    }

    public boolean containsNext() {
        return levelPack.levels.size() > lvl;
    }

    public ArrayList<ArrayList<ActorInfo>> getActorList() {
        return actorList;
    }

    void reloadActorList() { //startNewLevel если отдельно взять метод
        actorList = FlowRush.getInstance().getGson().fromJson(level.actorListJson, new TypeToken<ArrayList<ArrayList<ActorInfo>>>() {
        }.getType());
    }

    public int getPack() {
        return pack;
    }

    public int getLvl() {
        return lvl;
    }

    private void checkPackProgress() {
        if (FlowRush.getInstance().save.getLevelsProgress()[getPack() - 1] < FlowRush.getInstance().save.getCurrentLvl()) { //здесь мы обновляем прогресс пака
            //System.out.println("pack progress saved");
            FlowRush.getInstance().save.setLevelsProgress(getPack() - 1, getLvl());
        }
    }

    void prevLvl() {
        lvl--;
        //System.out.println("previous "+pack+"-pack, "+lvl+"-lvl.");
        level = levelPack.levels.get(lvl - 1);

        reloadActorList();

        saveToPrefs();
    }

    public Packs.LevelPack getLevelPack(int x) {
        return packs.packsArray.get(x);
    }
}