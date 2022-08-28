package com.sekarre.helpcenternotification.listeners;

import com.sekarre.helpcenternotification.DTO.NotificationLimiterQueueDTO;
import com.sekarre.helpcenternotification.DTO.NotificationQueueDTO;
import com.sekarre.helpcenternotification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${notification.queue.name}", containerFactory = "notificationListenerContainerFactory")
    public void receiveNewNotification(@Payload NotificationQueueDTO notificationQueueDTO) {
      log.debug(notificationQueueDTO.toString());
        notificationService.saveAndSendNotification(notificationQueueDTO);
    }

    @RabbitListener(queues = "${notification.limiter.queue.name}", containerFactory = "notificationListenerContainerFactory")
    public void receiveNewNotificationLimiter(@Payload NotificationLimiterQueueDTO notificationLimiterQueueDTO) {
        log.debug(notificationLimiterQueueDTO.toString());
        if (notificationLimiterQueueDTO.isLimited()) {
            notificationService.startNotificationForDestination(notificationLimiterQueueDTO);
        } else {
            notificationService.stopNotificationForDestination(notificationLimiterQueueDTO);
        }
    }
}
