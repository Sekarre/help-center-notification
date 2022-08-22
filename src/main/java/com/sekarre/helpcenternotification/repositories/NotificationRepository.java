package com.sekarre.helpcenternotification.repositories;

import com.sekarre.helpcenternotification.domain.Notification;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserIdAndReadIsFalse(Long userId);

    List<Notification> findAllByDestinationIdAndUserIdAndEventType(String destinationId, Long userId, EventType eventType);

    Optional<Notification> findFirstByDestinationIdAndUserIdAndEventType(String destinationId, Long userId, EventType eventType);

    Long countAllByDestinationIdAndEventTypeAndUserIdAndReadIsFalse(String destinationId, EventType eventType, Long userId);
}
