package com.blackhornetworkshop.flowrush.model.ex;

//Created by TScissors.

public class FlowRushException extends RuntimeException{

    public FlowRushException() {}

    public FlowRushException(String message) {
        super(message);
    }

    public FlowRushException(Throwable cause) {
        super(cause);
    }

    public FlowRushException(String message, Throwable cause) {
        super(message, cause);
    }
}
