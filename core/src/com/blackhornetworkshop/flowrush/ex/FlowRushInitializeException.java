package com.blackhornetworkshop.flowrush.ex;

//Created by TScissors.

public class FlowRushInitializeException extends RuntimeException{

    public FlowRushInitializeException() {}

    public FlowRushInitializeException(String message) {
        super(message);
    }

    public FlowRushInitializeException(Throwable cause) {
        super(cause);
    }

    public FlowRushInitializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
