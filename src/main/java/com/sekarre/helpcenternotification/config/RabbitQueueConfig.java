package com.sekarre.helpcenternotification.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {

    @Value("${notification.queue.name}")
    private String queueName;

    @Bean
    public Queue notificationQueue() {
        return new Queue(queueName, true);
    }
}
