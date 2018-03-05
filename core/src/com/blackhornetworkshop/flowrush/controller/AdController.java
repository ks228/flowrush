package com.blackhornetworkshop.flowrush.controller;

//Created by TScissors.

import com.blackhornetworkshop.flowrush.model.FlowRush;

public class AdController {

    private static boolean showAdOnNextScreen = false;
    private static boolean isAdLoaded = false;

    public static void setIsAdLoaded(){
        isAdLoaded = true;
    }

    public static void setShowAdOnNextScreen(boolean showAdOnNextScreen) {
        FlowRush.logDebug("Show ad on next screen: " + showAdOnNextScreen);
        AdController.showAdOnNextScreen = showAdOnNextScreen;
    }

    static void showAd() {
        if(!FlowRush.getPreferences().isAdsRemoved()) {
            if(FlowRush.getAndroidHelper().isInternetConnected()) {
                FlowRush.logDebug("Show ad");
                isAdLoaded = false;
                showAdOnNextScreen = false;
                FlowRush.getAndroidHelper().showAd();
            }else{
                FlowRush.getAndroidHelper().logDebug("Don't show ad, because internet is off");
            }
        }else{
            FlowRush.logDebug("Skip showing ad, because ads removed");
        }
    }

    static boolean isShowAdOnNextScreen() {
        return showAdOnNextScreen;
    }

    static boolean isAdLoaded(){ return isAdLoaded;}
}
