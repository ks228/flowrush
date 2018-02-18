package com.blackhornetworkshop.flowrush.controller;

//Created by TScissors.

import com.blackhornetworkshop.flowrush.view.FlowRush;

public class AdController {

    private static boolean isAdLoaded = false;
    private static boolean showAdOnNextScreen = false;

    public static void setAdLoaded(){
        isAdLoaded = true;
    }

    public static void setShowAdOnNextScreen(boolean showAdOnNextScreen){
        FlowRush.logDebug("setShowAdOnNextScreen() " + showAdOnNextScreen);
        AdController.showAdOnNextScreen = showAdOnNextScreen;
    }

    static void showAd(){
        FlowRush.logDebug("Show ad");

        isAdLoaded = false;
        showAdOnNextScreen = false;

        FlowRush.getAndroidHelper().showAd();
    }

    static boolean isShowAdOnNextScreen(){
        return showAdOnNextScreen;
    }

    static boolean isAdLoaded(){
        return isAdLoaded;
    }
}
