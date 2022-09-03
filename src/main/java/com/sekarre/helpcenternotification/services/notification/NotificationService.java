package com.sekarre.helpcenternotification.services.notification;


import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.DTO.NotificationLimiterQueueDTO;
import com.sekarre.helpcenternotification.DTO.NotificationQueueDTO;
import com.sekarre.helpcenternotification.domain.enums.EventType;

import java.util.List;

public interface NotificationService {

    void saveAndSendNotification(NotificationQueueDTO notificationQueueDTO);

    void saveNotification(EventType eventType, String destinationId, Long userId);

    List<NotificationDTO> getAllUnreadNotifications();

    Integer getNotificationCount(String destinationId, EventType eventType);

    void markNotificationAsRead(String destinationId, EventType eventType);

    void stopNotificationForDestination(NotificationLimiterQueueDTO notificationLimiterQueueDTO);


    boolean isNotificationStopped(String destinationId, Long userId, EventType eventType);

    void startNotificationForDestination(NotificationLimiterQueueDTO notificationLimiterQueueDTO);
}
