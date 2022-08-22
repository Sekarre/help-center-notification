package com.sekarre.helpcenternotification.listeners;

import com.sekarre.helpcentercore.DTO.notification.NotificationQueueDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationListener {

    @RabbitListener(queues = "${notification.queue.name}")
    public void receiveNewNotification(@Payload NotificationQueueDTO notificationQueueDTO) {
      log.debug(notificationQueueDTO.toString());
    }
}
