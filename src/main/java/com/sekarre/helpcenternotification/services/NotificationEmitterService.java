package com.sekarre.helpcenternotification.services;

import com.sekarre.helpcenternotification.DTO.NotificationQueueDTO;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationEmitterService {

    void removeEmitter(Long userId);

    SseEmitter createNewEmitter();

    void sendNotification(EventType eventType, String destinationId, Long[] usersId);

    void sendNotification(NotificationQueueDTO notificationQueueDTO);
}
