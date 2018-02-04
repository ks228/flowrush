package com.blackhornetworkshop.flowrush.initialization;

// Created by TScissors. Класс который хранит информацию о позиции, повороте и типе гекс ячейки.

public class ActorInfo {

    private int index; //порядковый индекс текстуры
    private int position; // позиция для выставления angle
    private int include; // 0 - без центра, 1 - с центром источником, 2 - с центром приемником, 3 - с центром передатчиком

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
