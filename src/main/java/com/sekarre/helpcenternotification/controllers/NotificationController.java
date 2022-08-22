package com.sekarre.helpcenternotification.controllers;

import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import com.sekarre.helpcenternotification.security.perms.NotificationPermission;
import com.sekarre.helpcenternotification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/event-notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getUnreadNotifications() {
        return notificationService.getAllUnreadNotifications();
    }

    @NotificationPermission
    @PatchMapping("/{destinationId}")
    public void markEventNotificationAsRead(@PathVariable String destinationId, @RequestParam String eventType) {
        notificationService.markNotificationAsRead(destinationId, Enum.valueOf(EventType.class, eventType));
    }

    @GetMapping("/{destinationId}/count")
    public Integer getEventNotificationCount(@PathVariable String destinationId, @RequestParam String eventType) {
        return notificationService.getNotificationCount(destinationId, Enum.valueOf(EventType.class, eventType));
    }
}
