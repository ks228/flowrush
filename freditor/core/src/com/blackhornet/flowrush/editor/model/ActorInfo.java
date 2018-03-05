package com.blackhornet.flowrush.editor.model;

// Created by TScissors.

public class ActorInfo {

    private int index;
    private int position;
    private int include = 0; // 0 - simple, 1 - with source, 2 - with destination point, 3 - with dove

    public ActorInfo(){
        index = 0;
        position = 0;
        include = 0;
    }

    public void setIndex(int index){
        this.index = index;
    }
    public void setPosition(int position){
        this.position = position;
    }
    public void setInclude(int include){
        this.include = include;
    }

    public int getIndex(){return index;}
    public int getPosition(){return position;}
    public int getInclude(){return include;}
}
