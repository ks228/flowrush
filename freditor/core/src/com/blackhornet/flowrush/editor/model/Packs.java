package com.blackhornet.flowrush.editor.model;

import java.util.ArrayList;

//Created by TScissors.

public class Packs {
    public ArrayList<LevelPack> packsArray = new ArrayList();

    public class LevelPack {
        public float price;
        public boolean available;
        public ArrayList<Packs.Level> levels;
    }
    public class Level {
        public int num;
        public String actorListJson;
    }
}