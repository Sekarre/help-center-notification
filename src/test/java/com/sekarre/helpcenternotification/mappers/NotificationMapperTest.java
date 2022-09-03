package com.sekarre.helpcenternotification.mappers;

import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.DTO.NotificationLimiterQueueDTO;
import com.sekarre.helpcenternotification.domain.Notification;
import com.sekarre.helpcenternotification.domain.NotificationLimiter;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import com.sekarre.helpcenternotification.testutil.JUnitMessageGenerator;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.sekarre.helpcenternotification.factories.NotificationMockFactory.getNotificationLimiterQueueDTOMock;
import static com.sekarre.helpcenternotification.factories.NotificationMockFactory.getNotificationMock;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationMapperTest {

    private final NotificationMapper notificationMapper = Mappers.getMapper(NotificationMapper.class);
    private JUnitMessageGenerator<?, ?> messageGenerator;

    @Test
    public void should_map_Notification_to_NotificationDTO() {
        messageGenerator = new JUnitMessageGenerator<>(Notification.class, NotificationDTO.class);

        //given
        final Notification from = getNotificationMock();

        //when
        NotificationDTO result = notificationMapper.mapNotificationToNotificationDTO(from);

        //then
        assertEquals(from.getId(), result.getId(),
                messageGenerator.getMessage("id", from.getId(), "id", result.getId()));
        assertEquals(from.getUserId(), result.getUserId(),
                messageGenerator.getMessage("userId", from.getUserId(), "userId", result.getUserId()));
        assertEquals(from.getMessage(), result.getMessage(),
                messageGenerator.getMessage("message", from.getMessage(), "message", result.getMessage()));
        assertEquals(from.getDestinationId(), result.getDestinationId(),
                messageGenerator.getMessage("destinationId", from.getDestinationId(), "destinationId", result.getDestinationId()));
        assertEquals(from.getEventType(), result.getEventType(),
                messageGenerator.getMessage("eventType", from.getEventType(), "eventType", result.getEventType()));
        assertEquals(from.getCreatedAt(), result.getCreatedAt(),
                messageGenerator.getMessage("createdAt", from.getCreatedAt(), "createdAt", result.getCreatedAt()));
    }

    @Test
    public void should_map_NotificationLimiterQueueDTO_to_NotificationLimiter() {
        messageGenerator = new JUnitMessageGenerator<>(NotificationLimiterQueueDTO.class, NotificationLimiter.class);

        //given
        final NotificationLimiterQueueDTO from = getNotificationLimiterQueueDTOMock();

        //when
        NotificationLimiter result = notificationMapper.mapNotificationLimiterQueueDTOToNotificationLimiter(from);

        //then
        assertEquals(from.getUserId(), result.getUserId(),
                messageGenerator.getMessage("userId", result.getUserId(), "userId", result.getUserId()));
        assertEquals(from.getDestinationId(), result.getDestinationId(),
                messageGenerator.getMessage("destinationId", result.getDestinationId(), "destinationId", result.getDestinationId()));
        assertEquals(Enum.valueOf(EventType.class, from.getEventType()), result.getEventType(),
                messageGenerator.getMessage("eventType", result.getEventType(), "eventType", result.getEventType()));
    }
}