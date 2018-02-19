package com.blackhornetworkshop.flowrush.controller;

//Created by TScissors.

import com.blackhornetworkshop.flowrush.model.ui.UIPool;
import com.blackhornetworkshop.flowrush.model.FlowRush;

import static com.blackhornetworkshop.flowrush.model.FRConstants.ANSWER_NO_1;
import static com.blackhornetworkshop.flowrush.model.FRConstants.ANSWER_NO_2;
import static com.blackhornetworkshop.flowrush.model.FRConstants.ANSWER_YES_1;
import static com.blackhornetworkshop.flowrush.model.FRConstants.ANSWER_YES_2;
import static com.blackhornetworkshop.flowrush.model.FRConstants.ANSWER_YES_3;
import static com.blackhornetworkshop.flowrush.model.FRConstants.QUESTION_1;
import static com.blackhornetworkshop.flowrush.model.FRConstants.QUESTION_2;
import static com.blackhornetworkshop.flowrush.model.FRConstants.QUESTION_3;

public class RateDialogController {

    private static boolean isFirstAnswer = true;
    private static boolean isFirstAnswerWasYes = true;

    public static void reset(){
        FlowRush.logDebug("RateDialogController reset() method called");
        isFirstAnswer = true;
        isFirstAnswer = true;
        UIPool.getDialogBackground().setText(QUESTION_1);
        UIPool.getRightButton().setText(ANSWER_YES_1);
        UIPool.getLeftButton().setText(ANSWER_NO_1);
    }

    public static boolean isFirstAnswer() {
        return isFirstAnswer;
    }

    public static void setIsFirstAnswerWasYes(boolean isFirstAnswerWasYes){
        isFirstAnswer = false;
        RateDialogController.isFirstAnswerWasYes = isFirstAnswerWasYes;
        if(isFirstAnswerWasYes) {
            UIPool.getDialogBackground().setText(QUESTION_2);
            UIPool.getRightButton().setText(ANSWER_YES_2);
            UIPool.getLeftButton().setText(ANSWER_NO_2);
        }else{
            UIPool.getDialogBackground().setText(QUESTION_3);
            UIPool.getRightButton().setText(ANSWER_YES_3);
            UIPool.getLeftButton().setText(ANSWER_NO_2);
        }
    }

    public static boolean isIsFirstAnswerWasYes(){return isFirstAnswerWasYes;}
}
