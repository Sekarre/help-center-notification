package com.sekarre.helpcenternotification.services;

import com.sekarre.helpcenternotification.domain.enums.EventType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationEmitterService {

    void removeEmitter(Long userId);

    SseEmitter createNewEmitter();

    /**
     * Sends new event message to current logged-in user
     * @param eventType
     * @param destinationId
     */
    void sendNewNotificationMessage(EventType eventType, String destinationId);

    /**
     * Sends new event message to all users with id in usersId parameter
     * @param eventType
     * @param destinationId
     * @param usersId
     */
    void sendNewNotificationMessage(EventType eventType, String destinationId, Long[] usersId);
}
