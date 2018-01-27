package com.blackhornetworkshop.flowrush;

//Created by TScissors. Интерфейс для поддержки методов AndroidApplication

public interface AndroidSide{
    void sendMail();
    void openFacebook();
    void openTwitter();
    void openVK();
    void openPlaymarket();
    boolean isDebug();
    void logError(String msg, Throwable tr);
    void logDebug(String msg);
}
