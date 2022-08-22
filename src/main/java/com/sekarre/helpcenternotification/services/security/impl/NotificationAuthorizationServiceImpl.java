package com.sekarre.helpcenternotification.services.security.impl;

import com.sekarre.helpcenternotification.domain.Notification;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import com.sekarre.helpcenternotification.exceptions.notification.NotificationAuthorizationException;
import com.sekarre.helpcenternotification.exceptions.notification.NotificationNotFoundException;
import com.sekarre.helpcenternotification.repositories.NotificationRepository;
import com.sekarre.helpcenternotification.services.security.NotificationAuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sekarre.helpcenternotification.security.UserDetailsHelper.getCurrentUser;


@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationAuthorizationServiceImpl implements NotificationAuthorizationService {

    private final NotificationRepository notificationRepository;

    @Override
    public boolean checkIfUserAuthorizedToEventNotification(String destinationId, EventType eventType) {
        if (!getEventNotificationByDestAndEventType(destinationId, eventType).getUserId().equals(getCurrentUser().getId())) {
            throw new NotificationAuthorizationException("User is not authorized to event notification");
        }
        return true;
    }

    private Notification getEventNotificationByDestAndEventType(String destinationId, EventType eventType) {
        return notificationRepository.findFirstByDestinationIdAndUserIdAndEventType(destinationId, getCurrentUser().getId(), eventType)
                .orElseThrow(() -> new NotificationNotFoundException("Event notification with destId: " + destinationId + " and " +
                        "event type: " + eventType + " not found"));
    }
}
