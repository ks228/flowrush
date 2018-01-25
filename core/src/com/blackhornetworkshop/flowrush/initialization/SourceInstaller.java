package com.blackhornetworkshop.flowrush.initialization;

//Created by TScissors. Кдасс для обработки типов и установки первоначального состояния источников.

public class SourceInstaller {

    public  static boolean[] getSourceArray(com.blackhornetworkshop.flowrush.gameplay.TileActor actor) { // МОЖНО ЛИ СДЕЛАТЬ БУЛЕАН ПРИМИТИВЫ А НЕ ОБЪЕКТЫ
        boolean[] sourceArray;
        switch(actor.getType()){
            case 1:
                sourceArray  = new boolean[] {false,false,true,false,false,true}; // 1
                break;
            case 2:
                sourceArray  = new boolean[] {false,false,true,false,true,false}; // 2
                break;
            case 3:
                sourceArray  = new boolean[] {true,false,true,false,true,false}; // 3
                break;
            case 4:
                sourceArray  = new boolean[] {false,false,true,true,false,true}; // 4
                break;
            case 5:
                sourceArray  = new boolean[] {false,true,true,false,false,true}; // 5
                break;
            case 6:
                sourceArray  = new boolean[] {true,true,true,true,true,true}; // 6
                break;
            case 7:
                sourceArray  = new boolean[] {false,false,true,true,true,true}; // 7
                break;
            case 8:
                sourceArray  = new boolean[] {true,false,true,true,true,false}; // 8
                break;
            case 9:
                sourceArray  = new boolean[] {false,false,true,true,false,false}; // 9
                break;
            case 10:
                sourceArray  = new boolean[] {false,false,true,true,true,false}; // 10
                break;
            case 11:
                sourceArray  = new boolean[] {true,true,false,true,true,false}; // 11
                break;
            case 12:
                sourceArray  = new boolean[] {true,true,true,true,true,false}; // 12
                break;
            case 13:
                sourceArray  = new boolean[] {false,false,true,false,false,false}; // 13
                break;
            default:
                sourceArray = null;
                break;
        }
        return sourceArray;
    }
}