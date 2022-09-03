package com.sekarre.helpcenternotification.factories;

import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.DTO.NotificationLimiterQueueDTO;
import com.sekarre.helpcenternotification.DTO.NotificationQueueDTO;
import com.sekarre.helpcenternotification.domain.Notification;
import com.sekarre.helpcenternotification.domain.NotificationLimiter;
import com.sekarre.helpcenternotification.domain.enums.EventType;

import static com.sekarre.helpcenternotification.util.DateUtil.getCurrentDateTime;
import static com.sekarre.helpcenternotification.util.DateUtil.getDateTimeFormatted;

public class NotificationMockFactory {

    public static Notification getNotificationMock() {
        return Notification.builder()
                .id(1L)
                .userId(1L)
                .message("Message")
                .destinationId("Destination")
                .eventType(EventType.ASSIGNED_TO_ISSUE)
                .createdAt(getCurrentDateTime())
                .build();
    }

    public static NotificationDTO getNotificationDTOMock() {
        return NotificationDTO.builder()
                .id(1L)
                .userId(1L)
                .message("Message")
                .destinationId("Destination")
                .eventType(EventType.ASSIGNED_TO_ISSUE)
                .createdAt(getCurrentDateTime())
                .build();
    }

    public static NotificationQueueDTO getNotificationQueueDTOMock() {
        return NotificationQueueDTO.builder()
                .id(1L)
                .userId(1L)
                .message("Message")
                .destinationId("Destination")
                .eventType(EventType.ASSIGNED_TO_ISSUE.name())
                .createdAt(getDateTimeFormatted(getCurrentDateTime()))
                .build();
    }

    public static NotificationLimiter getNotificationLimiterMock() {
        return NotificationLimiter.builder()
                .destinationId("Destination")
                .eventType(EventType.ASSIGNED_TO_ISSUE)
                .userId(1L)
                .build();
    }

    public static NotificationLimiterQueueDTO getNotificationLimiterQueueDTOMock() {
        return NotificationLimiterQueueDTO.builder()
                .destinationId("Destination")
                .eventType(EventType.ASSIGNED_TO_ISSUE.name())
                .userId(1L)
                .isLimited(true)
                .build();
    }
}
