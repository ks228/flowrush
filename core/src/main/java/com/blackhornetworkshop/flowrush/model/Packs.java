package com.blackhornetworkshop.flowrush.model;

import java.util.ArrayList;

//Created by TScissors. Класс для хранения всех паков и информации о них.

public class Packs {
    public ArrayList<LevelPack> packsArray = new ArrayList(); //хранит паки

    public class LevelPack { //хранит уровни пака
        public float price;
        public boolean available;
        public ArrayList<Packs.Level> levels;
    }
    public class Level { //хранит ActorList и номер уровня
        public String actorListJson;
    }
}
