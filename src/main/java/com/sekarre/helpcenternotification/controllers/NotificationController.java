package com.sekarre.helpcenternotification.controllers;

import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import com.sekarre.helpcenternotification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sekarre.helpcenternotification.controllers.NotificationController.BASE_EVENT_NOTIFICATION_URL;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE_EVENT_NOTIFICATION_URL)
public class NotificationController {

    public static final String BASE_EVENT_NOTIFICATION_URL = "/api/v1/event-notifications";

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getUnreadNotifications() {
        return notificationService.getAllUnreadNotifications();
    }

    @PatchMapping("/{destinationId}")
    public void markEventNotificationAsRead(@PathVariable String destinationId, @RequestParam String eventType) {
        notificationService.markNotificationAsRead(destinationId, Enum.valueOf(EventType.class, eventType));
    }

    @GetMapping("/{destinationId}/count")
    public Integer getEventNotificationCount(@PathVariable String destinationId, @RequestParam String eventType) {
        return notificationService.getNotificationCount(destinationId, Enum.valueOf(EventType.class, eventType));
    }
}
