package com.sekarre.helpcenternotification.mappers;

import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.DTO.NotificationLimiterQueueDTO;
import com.sekarre.helpcenternotification.domain.Notification;
import com.sekarre.helpcenternotification.domain.NotificationLimiter;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class NotificationMapper {

    public abstract NotificationDTO mapNotificationToNotificationDTO(Notification notification);

    public abstract NotificationLimiter mapNotificationLimiterQueueDTOToNotificationLimiter(NotificationLimiterQueueDTO notificationLimiterQueueDTO);
}
