package com.blackhornetworkshop.flowrush.ex;

//Created by TScissors.

public class FlowRushInitializationException extends RuntimeException{

    public FlowRushInitializationException() {}

    public FlowRushInitializationException(String message) {
        super(message);
    }

    public FlowRushInitializationException(Throwable cause) {
        super(cause);
    }

    public FlowRushInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
