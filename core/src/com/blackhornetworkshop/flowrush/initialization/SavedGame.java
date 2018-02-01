package com.blackhornetworkshop.flowrush.initialization;

//Created by TScissors. Файл с сохранением прогресса игрока

public class SavedGame {

    private String uniqSaveGameName;
    private boolean[] achievements = {false, false, false, false, false, false, false, false, false, false};
    private int currentLvl = 1;
    private int currentPack = 1;
    private int[] levelsProgress = {1, 1, 1, 1, 1};


    public void setUniqSaveName(){//Вызывать только когда создатся новый объект GamePreferences
        uniqSaveGameName = Long.toString(System.currentTimeMillis());
    }

    public void setUniqSaveName(String uniqSaveGameName) {
        this.uniqSaveGameName = uniqSaveGameName;
    }

    public String getUniqSnapshotName(){return uniqSaveGameName;}

    public boolean[] getAchievements(){return achievements;} //через этот же метод и устанавливаем

    public int getCurrentLvl(){return currentLvl;}
    public void setCurrentLvl(int lvl){ currentLvl=lvl;}
    public int getCurrentPack(){return currentPack;}
    public void setCurrentPack(int pack){currentPack = pack;}


    public int[] getLevelsProgress(){return  levelsProgress;}
    public void setLevelsProgress(int pos, int value){levelsProgress[pos]=value;}

    public String getPackName(){
        String string;
        switch (currentPack){
            case 1:
                string = "QUESTION";
                break;
            case 2:
                string = "IDEA";
                break;
            case 3:
                string = "MISSION";
                break;
            case 4:
                string = "FLYING";
                break;
            case 5:
                string = "SOURCE";
                break;
            default:
                string = "NULL";
                break;
        }

        return string;
    }
}
