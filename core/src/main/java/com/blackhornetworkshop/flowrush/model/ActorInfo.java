package com.blackhornetworkshop.flowrush.model;

// Created by TScissors.

public class ActorInfo {

    private int index; //texture index
    private int position; // angle
    private int include; // 1 - source. 2 - receiver. 3 - dove

    public void setIndex(int index){
        this.index = index;
    }
    public void setPosition(int position){
        this.position = position;
    }

    public int getIndex(){return index;}
    public int getPosition(){return position;}
    public int getInclude(){return include;}
}
