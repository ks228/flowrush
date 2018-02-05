package com.blackhornetworkshop.flowrush.initialization;

// Created by TScissors.

public class ActorInfo {

    private int index; //порядковый индекс текстуры
    private int position; // позиция для выставления angle
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
