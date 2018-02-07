package com.blackhornetworkshop.flowrush.controller;

//Created by TScissors.

import com.blackhornetworkshop.flowrush.model.ui.UIPool;

public class RateDialogController {

    private static boolean isFirstAnswer = true;

    public static void reset(){
        isFirstAnswer = true;
        UIPool.getDialogBackground().setText("RATE THE GAME, PLEASE");
        UIPool.getRightButton().setText("YES, SURE");
        UIPool.getLeftButton().setText("NO, THANKS");
    }

    public static boolean isFirstAnswer() {
        return isFirstAnswer;
    }

    public static void setIsFirstAnswer(boolean isFirstAnswer){
        RateDialogController.isFirstAnswer = isFirstAnswer;
    }


}
