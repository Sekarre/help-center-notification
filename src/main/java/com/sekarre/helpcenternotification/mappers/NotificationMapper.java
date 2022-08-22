package com.sekarre.helpcenternotification.mappers;

import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.domain.Notification;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class NotificationMapper {

    public abstract NotificationDTO mapNotificationToNotificationDTO(Notification notification);
}
