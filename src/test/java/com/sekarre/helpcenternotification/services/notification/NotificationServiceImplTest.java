package com.sekarre.helpcenternotification.services.notification;

import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.DTO.NotificationLimiterQueueDTO;
import com.sekarre.helpcenternotification.DTO.NotificationQueueDTO;
import com.sekarre.helpcenternotification.SecurityContextMockSetup;
import com.sekarre.helpcenternotification.domain.Notification;
import com.sekarre.helpcenternotification.domain.NotificationLimiter;
import com.sekarre.helpcenternotification.domain.User;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import com.sekarre.helpcenternotification.exceptions.notification.NotificationAuthorizationException;
import com.sekarre.helpcenternotification.mappers.NotificationMapper;
import com.sekarre.helpcenternotification.repositories.NotificationLimiterRepository;
import com.sekarre.helpcenternotification.repositories.NotificationRepository;
import com.sekarre.helpcenternotification.services.notification.NotificationServiceImpl;
import com.sekarre.helpcenternotification.services.notificationemitter.NotificationEmitterService;
import com.sekarre.helpcenternotification.services.notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static com.sekarre.helpcenternotification.factories.NotificationMockFactory.*;
import static com.sekarre.helpcenternotification.factories.UserMockFactory.getCurrentUserMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest extends SecurityContextMockSetup {

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private NotificationLimiterRepository notificationLimiterRepository;
    @Mock
    private NotificationMapper notificationMapper;
    @Mock
    private NotificationEmitterService notificationEmitterService;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        super.setUpSecurityContext();
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationServiceImpl(notificationRepository, notificationLimiterRepository, notificationMapper, notificationEmitterService);
    }

    @Test
    void should_save_and_send_notification() {
        //given
        final NotificationQueueDTO notificationQueueDTO = getNotificationQueueDTOMock();
        when(notificationLimiterRepository.existsByDestinationIdAndUserIdAndEventType(
                any(String.class), any(Long.class), any(EventType.class))).thenReturn(false);

        //when
        notificationService.saveAndSendNotification(notificationQueueDTO);

        //then
        verify(notificationEmitterService, times(1)).sendNotification(notificationQueueDTO);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void should_NOT_save_and_send_notification() {
        //given
        final NotificationQueueDTO notificationQueueDTO = getNotificationQueueDTOMock();
        when(notificationLimiterRepository.existsByDestinationIdAndUserIdAndEventType(
                any(String.class), any(Long.class), any(EventType.class))).thenReturn(true);

        //when
        notificationService.saveAndSendNotification(notificationQueueDTO);

        //then
        verify(notificationEmitterService, times(0)).sendNotification(any(NotificationQueueDTO.class));
        verify(notificationRepository, times(0)).save(any(Notification.class));
    }

    @Test
    void should_save_notification() {
        //given
        final EventType eventType = EventType.ASSIGNED_TO_ISSUE;
        final String destinationId = "123";
        final Long userId = 1L;
        final Notification expectedResult = Notification.builder().destinationId(destinationId).userId(userId).eventType(eventType).build();

        //when
        notificationService.saveNotification(eventType, destinationId, userId);

        //then
        verify(notificationRepository, times(1)).save(expectedResult);
    }

    @Test
    void should_get_all_unread_notifications() {
        //given
        final User currentUser = getCurrentUserMock();
        final NotificationDTO notificationDTO = getNotificationDTOMock();
        final Notification notification = getNotificationMock();
        setUpSecurityContext(currentUser);
        when(notificationRepository.findAllByUserIdAndReadIsFalse(any(Long.class))).thenReturn(Collections.singletonList(notification));
        when(notificationMapper.mapNotificationToNotificationDTO(any(Notification.class))).thenReturn(notificationDTO);

        //when
        List<NotificationDTO> response = notificationService.getAllUnreadNotifications();

        //then
        assertNotNull(response);
        assertEquals(response.get(0), notificationDTO, "NotificationDTO is not equal to response NotificationDTO");
        verify(notificationRepository, times(1)).findAllByUserIdAndReadIsFalse(currentUser.getId());
        verify(notificationMapper, times(1)).mapNotificationToNotificationDTO(notification);
    }

    @Test
    void should_get_notification_count() {
        //given
        final User currentUser = getCurrentUserMock();
        final EventType eventType = EventType.ASSIGNED_TO_ISSUE;
        final String destinationId = "123";
        final Long notificationCount = 2L;
        setUpSecurityContext(currentUser);
        when(notificationRepository.countAllByDestinationIdAndEventTypeAndUserIdAndReadIsFalse(
                any(String.class), any(EventType.class), any(Long.class))).thenReturn(notificationCount);

        //when
        Integer response = notificationService.getNotificationCount(destinationId, eventType);

        //then
        assertNotNull(response);
        assertEquals(response, notificationCount.intValue(), "Notification count is not equal to response notification count");
        verify(notificationRepository, times(1)).countAllByDestinationIdAndEventTypeAndUserIdAndReadIsFalse(
                destinationId, eventType, currentUser.getId());
    }

    @Test
    void should_mark_notification_as_read() {
        //given
        final String destinationId = "123";
        final EventType eventType = EventType.ASSIGNED_TO_ISSUE;
        final User currentUser = getCurrentUserMock();
        final Notification notification = getNotificationMock();
        notification.setUserId(currentUser.getId());
        setUpSecurityContext(currentUser);
        when(notificationRepository.findAllByDestinationIdAndUserIdAndEventType(
                any(String.class),  any(Long.class), any(EventType.class))).thenReturn(Collections.singletonList(notification));

        //when
        notificationService.markNotificationAsRead(destinationId, eventType);

        //then
        verify(notificationRepository, times(1)).findAllByDestinationIdAndUserIdAndEventType(destinationId, currentUser.getId(), eventType);
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void should_NOT_mark_notification_as_read_and_throw_NotificationAuthorizationException() {
        //given
        final String destinationId = "123";
        final EventType eventType = EventType.ASSIGNED_TO_ISSUE;
        final User currentUser = getCurrentUserMock();
        final Notification notification = getNotificationMock();
        setUpSecurityContext(currentUser);
        when(notificationRepository.findAllByDestinationIdAndUserIdAndEventType(
                any(String.class),  any(Long.class), any(EventType.class))).thenReturn(Collections.singletonList(notification));

        //when
        assertThrows(NotificationAuthorizationException.class, () -> notificationService.markNotificationAsRead(destinationId, eventType));

        //then
        verify(notificationRepository, times(1)).findAllByDestinationIdAndUserIdAndEventType(destinationId, currentUser.getId(), eventType);
        verify(notificationRepository, times(0)).save(any(Notification.class));
    }

    @Test
    void should_stop_notification_for_destination() {
        //given
        final NotificationLimiterQueueDTO notificationLimiterQueueDTO = getNotificationLimiterQueueDTOMock();
        final NotificationLimiter notificationLimiter = getNotificationLimiterMock();
        when(notificationMapper.mapNotificationLimiterQueueDTOToNotificationLimiter(notificationLimiterQueueDTO)).thenReturn(notificationLimiter);

        //when
        notificationService.stopNotificationForDestination(notificationLimiterQueueDTO);

        //then
        verify(notificationLimiterRepository, times(1)).save(notificationLimiter);
        verify(notificationMapper, times(1)).mapNotificationLimiterQueueDTOToNotificationLimiter(notificationLimiterQueueDTO);
    }

    @Test
    void should_confirm_that_notification_is_stopped() {
        //given
        final String destinationId = "123";
        final EventType eventType = EventType.ASSIGNED_TO_ISSUE;
        final User currentUser = getCurrentUserMock();
        when(notificationLimiterRepository.existsByDestinationIdAndUserIdAndEventType(
                any(String.class),  any(Long.class), any(EventType.class))).thenReturn(true);

        //when
        boolean response = notificationService.isNotificationStopped(destinationId, currentUser.getId(), eventType);

        //then
        assertTrue(response);
        verify(notificationLimiterRepository, times(1)).existsByDestinationIdAndUserIdAndEventType(destinationId, currentUser.getId(), eventType);
    }

    @Test
    void should_NOT_confirm_that_notification_is_stopped() {
        //given
        final String destinationId = "123";
        final EventType eventType = EventType.ASSIGNED_TO_ISSUE;
        final User currentUser = getCurrentUserMock();
        when(notificationLimiterRepository.existsByDestinationIdAndUserIdAndEventType(
                any(String.class),  any(Long.class), any(EventType.class))).thenReturn(false);

        //when
        boolean response = notificationService.isNotificationStopped(destinationId, currentUser.getId(), eventType);

        //then
        assertFalse(response);
        verify(notificationLimiterRepository, times(1)).existsByDestinationIdAndUserIdAndEventType(destinationId, currentUser.getId(), eventType);
    }

    @Test
    void should_start_notification_for_destination() {
        //given
        final NotificationLimiterQueueDTO notificationLimiterQueueDTO = getNotificationLimiterQueueDTOMock();

        //when
        notificationService.startNotificationForDestination(notificationLimiterQueueDTO);

        //then
        verify(notificationLimiterRepository, times(1)).deleteByDestinationIdAndUserIdAndEventType(
                        notificationLimiterQueueDTO.getDestinationId(),
                        notificationLimiterQueueDTO.getUserId(), Enum.valueOf(EventType.class,
                                notificationLimiterQueueDTO.getEventType()));
    }
}