package com.sekarre.helpcenternotification.controllers;

import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import com.sekarre.helpcenternotification.services.notification.NotificationService;
import com.sekarre.helpcenternotification.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.sekarre.helpcenternotification.factories.NotificationMockFactory.getNotificationDTOMock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;
    private MockMvc mockMvc;

    private NotificationController notificationController;
    private static final String BASE_URL = "/api/v1/event-notifications/";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationController = new NotificationController(notificationService);

        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    void should_return_unread_notifications_with_OK_status() throws Exception {
        //given
        final NotificationDTO notificationDTO = getNotificationDTOMock();
        when(notificationService.getAllUnreadNotifications()).thenReturn(Collections.singletonList(notificationDTO));

        //when + then
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(notificationDTO.getId()))
                .andExpect(jsonPath("$[0].userId").value(notificationDTO.getUserId()))
                .andExpect(jsonPath("$[0].message").value(notificationDTO.getMessage()))
                .andExpect(jsonPath("$[0].destinationId").value(notificationDTO.getDestinationId()))
                .andExpect(jsonPath("$[0].eventType").value(notificationDTO.getEventType().name()))
                .andExpect(jsonPath("$[0].createdAt").value(DateUtil.getDateTimeFormatted(notificationDTO.getCreatedAt())));

        verify(notificationService, times(1)).getAllUnreadNotifications();
    }


    @Test
    void should_mark_event_notification_as_read_and_return_OK_status() throws Exception {
        //given
        final String destinationId = "1sal";
        final EventType eventTypeName = EventType.CHAT_ALL;

        //when + then
        mockMvc.perform(patch(BASE_URL + destinationId).param("eventType", eventTypeName.name()))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).markNotificationAsRead(destinationId, eventTypeName);
    }

    @Test
    void should_return_event_notification_count_with_OK_status() throws Exception {
        //given
        final String destinationId = "1sal";
        final Integer notificationCount = 10;
        final EventType eventTypeName = EventType.CHAT_ALL;
        when(notificationService.getNotificationCount(any(String.class), any(EventType.class))).thenReturn(notificationCount);

        //when + then
        mockMvc.perform(get(BASE_URL + destinationId + "/count")
                        .param("eventType", eventTypeName.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(notificationService, times(1)).getNotificationCount(destinationId, eventTypeName);
    }
}