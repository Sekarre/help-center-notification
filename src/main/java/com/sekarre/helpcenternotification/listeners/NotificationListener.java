package com.sekarre.helpcenternotification.listeners;

import com.sekarre.helpcentercore.DTO.notification.NotificationQueueDTO;
import com.sekarre.helpcenternotification.services.NotificationEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationListener {

    private final NotificationEmitterService notificationEmitterService;

    @RabbitListener(queues = "${notification.queue.name}")
    public void receiveNewNotification(@Payload NotificationQueueDTO notificationQueueDTO) {
      log.debug(notificationQueueDTO.toString());
      notificationEmitterService.saveAndSendNotification(notificationQueueDTO);
    }
}
