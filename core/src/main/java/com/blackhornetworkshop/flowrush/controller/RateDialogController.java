package com.blackhornetworkshop.flowrush.controller;

//Created by TScissors.

import com.blackhornetworkshop.flowrush.model.ui.UIPool;
import com.blackhornetworkshop.flowrush.view.FlowRush;

public class RateDialogController {

    private static boolean isFirstAnswer = true;
    private static boolean isFirstAnswerWasYes = true;

    public static void reset(){
        FlowRush.logDebug("RateDialogController reset() method called");
        isFirstAnswer = true;
        isFirstAnswer = true;
        UIPool.getDialogBackground().setText("ENJOYING  FLOW RUSH?");
        UIPool.getRightButton().setText("YES!");
        UIPool.getLeftButton().setText("NOT SURE");
    }

    public static boolean isFirstAnswer() {
        return isFirstAnswer;
    }

    public static void setIsFirstAnswerWasYes(boolean isFirstAnswerWasYes){
        isFirstAnswer = false;
        RateDialogController.isFirstAnswerWasYes = isFirstAnswerWasYes;
        if(isFirstAnswerWasYes) {
            UIPool.getDialogBackground().setText("RATE THE GAME, PLEASE");
            UIPool.getRightButton().setText("YES, SURE");
            UIPool.getLeftButton().setText("NO, THANKS");
        }else{
            UIPool.getDialogBackground().setText("SEND US FEEDBACK, PLEASE");
            UIPool.getLeftButton().setText("NO, THANKS");
            UIPool.getRightButton().setText("OK");
        }
    }

    public static boolean isIsFirstAnswerWasYes(){return isFirstAnswerWasYes;}
}
