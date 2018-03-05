package com.blackhornet.flowrush.editor.model;

import com.badlogic.gdx.Gdx;
import com.blackhornet.flowrush.editor.controller.FlowRushEditor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//Created by TScissors.

public class Packer {
    private Packs packs;
    public Packer(){
        packs = new Packs();
    }

    private ArrayList<Packs.Level> fillUpLevelPack(int packNum){
        ArrayList<Packs.Level> levelPack = new ArrayList<Packs.Level>();
        try {
            for (int i = 1; Gdx.files.internal("packs/pack"+packNum+"/lvl"+i+".json").exists(); i++) {
                Packs.Level level = packs.new Level();
                level.num = i;
                level.actorListJson = Gdx.files.internal("packs/pack"+packNum+"/lvl"+i+".json").readString();
                System.out.println(level.actorListJson);
                levelPack.add(level);
            }
        }
        catch (Exception ex) {
            levelPack = null;
            System.out.println("Pack "+packNum+" not found!");
        }
        return levelPack;
    }

    public void fillUpPacks(){
        for (int i = 1; i<6; i++) {
            Packs.LevelPack levelPack = packs.new LevelPack();
            levelPack.available = true;
            levelPack.price = 0;
            levelPack.levels = fillUpLevelPack(i);
            packs.packsArray.add(levelPack);
        }

        String string = FlowRushEditor.getGson().toJson(packs);
        try {
            FileWriter fileWriter = new FileWriter("levels.json");
            fileWriter.write(string);
            fileWriter.flush();
            fileWriter.close();
            System.out.println(string);
            System.out.println("File levels.json created");
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }
}