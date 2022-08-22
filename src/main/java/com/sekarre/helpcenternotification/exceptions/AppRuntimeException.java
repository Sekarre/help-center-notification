package com.sekarre.helpcenternotification.exceptions;

public abstract class AppRuntimeException extends RuntimeException {

    public AppRuntimeException(String message) {
        super(message);
    }
}
