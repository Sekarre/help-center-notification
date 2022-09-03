package com.sekarre.helpcenternotification.controllers;

import com.sekarre.helpcenternotification.services.notificationemitter.NotificationEmitterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationEmitterControllerTest {
    @Mock
    private NotificationEmitterService notificationEmitterService;
    private MockMvc mockMvc;

    private NotificationEmitterController notificationEmitterController;
    private static final String BASE_URL = "/api/v1/sse";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationEmitterController = new NotificationEmitterController(notificationEmitterService);

        mockMvc = MockMvcBuilders.standaloneSetup(notificationEmitterController).build();
    }

    @Test
    void should_return_unread_notifications_with_OK_status() throws Exception {
        //when + then
        mockMvc.perform(get(BASE_URL + "/events"))
                .andExpect(status().isOk());

        verify(notificationEmitterService, times(1)).createNewEmitter();
    }
}