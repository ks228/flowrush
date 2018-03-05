package com.blackhornet.flowrush.editor.model;

import java.util.ArrayList;

//Created by TScissors.

public class ActorListCreator {

    private ArrayList<ArrayList<com.blackhornet.flowrush.editor.model.ActorInfo>> actorList;

    private int lengthX;
    private int lengthY;

    public ActorListCreator(int lengthX, int lengthY){
        actorList = new ArrayList<ArrayList<com.blackhornet.flowrush.editor.model.ActorInfo>>();
        this.lengthX = lengthX;
        this.lengthY = lengthY;

        for (int i = 0; i < lengthX; i++) {
            ArrayList<com.blackhornet.flowrush.editor.model.ActorInfo> row = new ArrayList<com.blackhornet.flowrush.editor.model.ActorInfo>();
            actorList.add(row);
        }
        for (int x = 0; x < lengthX; x++) {
            for (int y = 0; y < lengthY; y++) {
                com.blackhornet.flowrush.editor.model.ActorInfo actorInfo = new com.blackhornet.flowrush.editor.model.ActorInfo();
                actorList.get(x).add(y, actorInfo);
                System.out.println("ActorInfo on position "+x+""+y+" created!");
            }
        }
    }


    public com.blackhornet.flowrush.editor.model.ActorInfo getActorInfo(int x, int y) {
        return actorList.get(x).get(y);
    }

    public int getHeight(){return lengthY;}
    public int getWidth(){return lengthX;}

    public ArrayList<ArrayList<com.blackhornet.flowrush.editor.model.ActorInfo>> getActorList(){
        return actorList;
    }
    public void setActorList(ArrayList<ArrayList<com.blackhornet.flowrush.editor.model.ActorInfo>> list){
        actorList = list;
    }
}