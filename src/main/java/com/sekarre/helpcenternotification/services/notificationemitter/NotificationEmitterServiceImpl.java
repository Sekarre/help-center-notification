package com.sekarre.helpcenternotification.services.notificationemitter;

import com.sekarre.helpcenternotification.DTO.NotificationQueueDTO;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.sekarre.helpcenternotification.security.UserDetailsHelper.getCurrentUser;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationEmitterServiceImpl implements NotificationEmitterService {

    @Value("${notification.emitter.timeout:7200000}")
    private Long timeout;
    final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    @Override
    public boolean removeEmitter(Long userId) {
        SseEmitter removed = emitterMap.remove(userId);
        return !Objects.isNull(removed);
    }

    @Override
    public SseEmitter createNewEmitter() {
        final Long userId = getCurrentUser().getId();
        if (emitterMap.containsKey(userId)) {
            return emitterMap.get(userId);
        }
        final SseEmitter sseEmitter = new SseEmitter(timeout);
        sseEmitter.onCompletion(() -> {
            log.debug("Emitter with id: " + userId + " successfully finished task");
            removeEmitter(userId);
        });
        sseEmitter.onTimeout(() -> {
            log.debug("Emitter with uuid: " + userId + " couldn't finish task before timeout");
            sseEmitter.complete();
            removeEmitter(userId);
        });
        emitterMap.put(userId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public boolean sendNotification(NotificationQueueDTO notificationQueueDTO) {
        final EventType eventType = Enum.valueOf(EventType.class, notificationQueueDTO.getEventType());
        final String destinationId = notificationQueueDTO.getDestinationId();
        final Long userId = notificationQueueDTO.getUserId();
        return sendNotification(eventType, destinationId, userId);
    }

    private boolean sendNotification(EventType eventType, String destinationId, Long userId) {
        try {
            if (emitterMap.containsKey(userId)) {
                emitterMap.get(userId).send(SseEmitter.event().name(eventType.name()).data(destinationId).build());
                return true;
            }
            return false;
        } catch (IOException e) {
            log.debug("Emitter send event failed for id: " + userId + " and event: " + eventType);
            return false;
        }
    }
}

