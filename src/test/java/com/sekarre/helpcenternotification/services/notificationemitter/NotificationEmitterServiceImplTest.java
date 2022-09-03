package com.sekarre.helpcenternotification.services.notificationemitter;

import com.sekarre.helpcenternotification.DTO.NotificationQueueDTO;
import com.sekarre.helpcenternotification.SecurityContextMockSetup;
import com.sekarre.helpcenternotification.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static com.sekarre.helpcenternotification.factories.NotificationMockFactory.getNotificationQueueDTOMock;
import static com.sekarre.helpcenternotification.factories.UserMockFactory.getUserWithGivenIdMock;
import static org.junit.jupiter.api.Assertions.*;

class NotificationEmitterServiceImplTest extends SecurityContextMockSetup {


    private NotificationEmitterServiceImpl notificationEmitterService;

    @BeforeEach
    void setUp() {
        super.setUpSecurityContext();
        MockitoAnnotations.openMocks(this);
        notificationEmitterService = new NotificationEmitterServiceImpl();
    }

    @Test
    void should_NOT_remove_emitter_and_return_false() {
        //given
        final Long userId = 2L;
        final User user = getUserWithGivenIdMock(userId);
        setUpSecurityContext(user);
        notificationEmitterService.emitterMap.put(userId, new SseEmitter());

        //when
        boolean response = notificationEmitterService.removeEmitter(1L);

        //then
        assertFalse(response);
        assertEquals(1, notificationEmitterService.emitterMap.size());
    }

    @Test
    void should_remove_emitter_and_return_true() {
        //given
        final Long userId = 1L;
        final User user = getUserWithGivenIdMock(userId);
        setUpSecurityContext(user);
        notificationEmitterService.emitterMap.put(userId, new SseEmitter());

        //when
        boolean response = notificationEmitterService.removeEmitter(userId);

        //then
        assertTrue(response);
        assertEquals(0, notificationEmitterService.emitterMap.size());
    }

    @Test
    void should_create_new_emitter() {
        //given
        final Long userId = 1L;
        final User user = getUserWithGivenIdMock(userId);
        setUpSecurityContext(user);

        //when
        SseEmitter response = notificationEmitterService.createNewEmitter();

        //then
        assertNotNull(response);
        assertEquals(1, notificationEmitterService.emitterMap.size());
    }

    @Test
    void should_send_notification_based_on_NotificationQueueDTO_and_return_true() {
        //given
        final Long userId = 1L;
        final User user = getUserWithGivenIdMock(userId);
        final NotificationQueueDTO notificationQueueDTO = getNotificationQueueDTOMock();
        setUpSecurityContext(user);
        notificationEmitterService.emitterMap.put(userId, new SseEmitter());

        //when
        boolean response = notificationEmitterService.sendNotification(notificationQueueDTO);

        //then
        assertTrue(response);
    }

    @Test
    void should_NOT_send_notification_based_on_NotificationQueueDTO_and_return_false() {
        //given
        final Long userId = 1L;
        final User user = getUserWithGivenIdMock(userId);
        final NotificationQueueDTO notificationQueueDTO = getNotificationQueueDTOMock();
        setUpSecurityContext(user);

        //when
        boolean response = notificationEmitterService.sendNotification(notificationQueueDTO);

        //then
        assertFalse(response);
    }
}