package com.sekarre.helpcenternotification.repositories;

import com.sekarre.helpcenternotification.domain.NotificationLimiter;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationLimiterRepository extends JpaRepository<NotificationLimiter, Long> {

    boolean existsByDestinationIdAndUserIdAndEventType(String destinationId, Long userId, EventType eventType);

    @Transactional
    void deleteByDestinationIdAndUserIdAndEventType(String destinationId, Long userId, EventType eventType);
}
