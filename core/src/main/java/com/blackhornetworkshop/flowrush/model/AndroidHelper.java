package com.blackhornetworkshop.flowrush.model;

//Created by TScissors.

public interface AndroidHelper {
    void sendMail();
    void openFacebook();
    void openTwitter();
    void openVK();
    void openPlayMarket();
    void logError(String msg, Throwable tr);
    void logDebug(String msg);
}
