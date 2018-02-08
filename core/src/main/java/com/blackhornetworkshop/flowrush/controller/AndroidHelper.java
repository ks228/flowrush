package com.blackhornetworkshop.flowrush.controller;

//Created by TScissors. Интерфейс для поддержки методов AndroidApplication

public interface AndroidHelper {
    void sendMail();
    void openFacebook();
    void openTwitter();
    void openVK();
    void openPlaymarket();
    void logError(String msg, Throwable tr);
    void logDebug(String msg);
}
