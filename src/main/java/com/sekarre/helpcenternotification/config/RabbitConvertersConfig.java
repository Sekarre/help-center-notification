package com.sekarre.helpcenternotification.config;

import com.sekarre.helpcenternotification.DTO.NotificationLimiterQueueDTO;
import com.sekarre.helpcenternotification.DTO.NotificationQueueDTO;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConvertersConfig {

    @Bean
    public SimpleRabbitListenerContainerFactory notificationListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                                     Jackson2JsonMessageConverter jsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        jsonMessageConverter.setClassMapper(classMapper());
        factory.setMessageConverter(jsonMessageConverter);
        return factory;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        classMapper.setTrustedPackages("notificationQueueDTO");
        classMapper.setTrustedPackages("notificationLimiterQueueDTO");
        idClassMapping.put("notificationQueueDTO", NotificationQueueDTO.class);
        idClassMapping.put("notificationLimiterQueueDTO", NotificationLimiterQueueDTO.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());
        return converter;
    }
}
