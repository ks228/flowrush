package com.blackhornetworkshop.flowrush.model;

//Created by TScissors.

public interface AndroidHelper {
    void openSocialNetworksActivity();
    void openWebsite();
    void sendMail();
    void openPlayMarket();
    void showAd();
    void logError(String msg, Throwable tr);
    void logDebug(String msg);
    void showToast(String msg);
    boolean isInternetConnected();

}
