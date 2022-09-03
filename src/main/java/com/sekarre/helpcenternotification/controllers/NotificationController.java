package com.sekarre.helpcenternotification.controllers;

import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import com.sekarre.helpcenternotification.services.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications() {
        return ResponseEntity.ok(notificationService.getAllUnreadNotifications());
    }

    @PatchMapping("/{destinationId}")
    public ResponseEntity<?> markEventNotificationAsRead(@PathVariable String destinationId, @RequestParam String eventType) {
        notificationService.markNotificationAsRead(destinationId, Enum.valueOf(EventType.class, eventType));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{destinationId}/count")
    public ResponseEntity<Integer> getEventNotificationCount(@PathVariable String destinationId, @RequestParam String eventType) {
        return ResponseEntity.ok(notificationService.getNotificationCount(destinationId, Enum.valueOf(EventType.class, eventType)));
    }
}
