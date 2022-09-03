package com.sekarre.helpcenternotification.services.notificationemitter;

import com.sekarre.helpcenternotification.DTO.NotificationQueueDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationEmitterService {

    boolean removeEmitter(Long userId);

    SseEmitter createNewEmitter();

    boolean sendNotification(NotificationQueueDTO notificationQueueDTO);
}
