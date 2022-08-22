package com.sekarre.helpcenternotification.services.security;


import com.sekarre.helpcenternotification.domain.enums.EventType;

public interface NotificationAuthorizationService {

    boolean checkIfUserAuthorizedToEventNotification(String destinationId, EventType eventType);
}
