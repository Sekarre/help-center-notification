package com.sekarre.helpcenternotification.exceptions.notification;


import com.sekarre.helpcenternotification.exceptions.AppRuntimeException;

public abstract class AppEventNotificationException extends AppRuntimeException {

    public AppEventNotificationException(String message) {
        super(message);
    }
}
