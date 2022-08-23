package com.sekarre.helpcenternotification.services;


import com.sekarre.helpcentercore.DTO.notification.NotificationQueueDTO;
import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.domain.enums.EventType;

import java.util.List;

public interface NotificationService {

    void saveNotification(EventType eventType, String destinationId, Long userId);

    List<NotificationDTO> getAllUnreadNotifications();

    Integer getNotificationCount(String destinationId, EventType eventType);

    void markNotificationAsRead(String destinationId, EventType eventType);

    void markNotificationAsRead(String destinationId, EventType... eventType);

    void stopNotificationForDestination(String destinationId, Long userId, EventType eventType);


    boolean isNotificationStopped(String destinationId, Long userId, EventType eventType);

    void startNotificationForDestination(String destinationId, Long userId, EventType eventType);
}
