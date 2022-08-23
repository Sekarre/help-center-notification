package com.sekarre.helpcenternotification.services;

import com.sekarre.helpcentercore.DTO.notification.NotificationQueueDTO;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationEmitterService {

    void removeEmitter(Long userId);

    SseEmitter createNewEmitter();

    void saveAndSendNotification(EventType eventType, String destinationId, Long[] usersId);

    void saveAndSendNotification(NotificationQueueDTO notificationQueueDTO);
}
