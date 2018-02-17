package com.blackhornetworkshop.flowrush.model;

//Created by TScissors.

public interface AndroidHelper {
    void openSocialNetworksActivity();
    void openWebsite();
    void sendMail();
    void openPlayMarket();
    void loadAndShowAd();
    void logError(String msg, Throwable tr);
    void logDebug(String msg);

}
