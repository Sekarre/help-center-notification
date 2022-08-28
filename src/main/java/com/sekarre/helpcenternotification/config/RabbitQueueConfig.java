package com.sekarre.helpcenternotification.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {

    @Value("${notification.queue.name}")
    private String notificationQueueName;

    @Value("${notification.limiter.queue.name}")
    private String notificationQueueLimiterName;

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueueName, true);
    }

    @Bean
    public Queue notificationLimiterQueue() {
        return new Queue(notificationQueueLimiterName, true);
    }
}
