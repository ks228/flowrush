package com.blackhornetworkshop.flowrush;

//Created by TScissors.

import android.util.Log;

public class FRLogger {
    private static final boolean isDebug = true;
    private static final String TAG = "FlowRush";

    public static void logError(String msg, Throwable tr) {
        Log.e(TAG, msg, tr);
    }

    public static void logDebug(String msg) {
        if(isDebug) Log.d(TAG, msg);
    }

    public static boolean isDebug() {return isDebug;}
}
