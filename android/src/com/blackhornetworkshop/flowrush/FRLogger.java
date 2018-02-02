package com.blackhornetworkshop.flowrush;

//Created by TScissors.

import android.util.Log;

class FRLogger {
    private static final boolean isDebug = true;
    private static final String TAG = "FlowRush";

    static void logError(String msg, Throwable tr) {
        Log.e(TAG, msg, tr);
    }

    static void logDebug(String msg) {
        if(isDebug) Log.d(TAG, msg);
    }

    static boolean isDebug() {return isDebug;}
}
