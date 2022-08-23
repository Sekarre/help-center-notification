package com.sekarre.helpcenternotification.services.impl;

import com.sekarre.helpcenternotification.DTO.NotificationDTO;
import com.sekarre.helpcenternotification.domain.Notification;
import com.sekarre.helpcenternotification.domain.NotificationLimiter;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import com.sekarre.helpcenternotification.exceptions.notification.NotificationAuthorizationException;
import com.sekarre.helpcenternotification.mappers.NotificationMapper;
import com.sekarre.helpcenternotification.repositories.NotificationLimiterRepository;
import com.sekarre.helpcenternotification.repositories.NotificationRepository;
import com.sekarre.helpcenternotification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sekarre.helpcenternotification.security.UserDetailsHelper.getCurrentUser;


@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationLimiterRepository notificationLimiterRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void saveNotification(EventType eventType, String destinationId, Long userId) {
        notificationRepository.save(Notification.builder()
                .destinationId(destinationId)
                .userId(userId)
                .eventType(eventType)
                .build());
    }

    @Override
    public List<NotificationDTO> getAllUnreadNotifications() {
        return notificationRepository.findAllByUserIdAndReadIsFalse(getCurrentUser().getId()).stream()
                .map(notificationMapper::mapNotificationToNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getNotificationCount(String destinationId, EventType eventType) {
        return notificationRepository.countAllByDestinationIdAndEventTypeAndUserIdAndReadIsFalse(
                destinationId, eventType, getCurrentUser().getId()).intValue();
    }

    @Override
    public void markNotificationAsRead(String destinationId, EventType eventType) {
        switch (eventType) {
            case CHAT_ALL -> {
                marAsRead(destinationId, EventType.REMOVED_FROM_CHAT);
                marAsRead(destinationId, EventType.NEW_CHAT_MESSAGE);
            }
            case ISSUE_ALL -> {
                marAsRead(destinationId, EventType.ASSIGNED_TO_ISSUE);
                marAsRead(destinationId, EventType.NEW_ISSUE);
                marAsRead(destinationId, EventType.NEW_ISSUE_COMMENT);
            }
            default -> marAsRead(destinationId, eventType);
        }
    }

    private void marAsRead(String destinationId, EventType eventType) {
        List<Notification> notifications = notificationRepository
                .findAllByDestinationIdAndUserIdAndEventType(destinationId, getCurrentUser().getId(), eventType);
        if (notifications.isEmpty()) {
            return;
        }
        checkIfAuthorizedToEditNotification(notifications.get(0));
        notifications.forEach(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    @Override
    public void markNotificationAsRead(String destinationId, EventType... eventTypes) {
        for (EventType et : eventTypes) {
            markNotificationAsRead(destinationId, et);
        }
    }

    @Override
    public void stopNotificationForDestination(String destinationId, Long userId, EventType eventType) {
        notificationLimiterRepository.save(NotificationLimiter.builder().destinationId(destinationId).eventType(eventType).userId(userId).build());
    }

    @Override
    public boolean isNotificationStopped(String destinationId, Long userId, EventType eventType) {
        return notificationLimiterRepository.existsByDestinationIdAndUserIdAndEventType(destinationId, userId, eventType);
    }

    @Override
    public void startNotificationForDestination(String destinationId, Long userId, EventType eventType) {
        notificationLimiterRepository.deleteByDestinationIdAndUserIdAndEventType(destinationId, userId, eventType);
    }

    private void checkIfAuthorizedToEditNotification(Notification notification) {
        Long userId = getCurrentUser().getId();
        if (!notification.getUserId().equals(userId)) {
            throw new NotificationAuthorizationException(
                    "User with id: + " + userId + "is not authorized to edit notification with id: " + notification.getId());
        }
    }
}
