package com.sekarre.helpcenternotification.services.impl;

import com.sekarre.helpcenternotification.DTO.NotificationQueueDTO;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import com.sekarre.helpcenternotification.services.NotificationEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.sekarre.helpcenternotification.security.UserDetailsHelper.getCurrentUser;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationEmitterServiceImpl implements NotificationEmitterService {

    public static final long TIMEOUT = 7_200_000L; //2hours
    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    @Override
    public void removeEmitter(Long userId) {
        emitterMap.remove(userId);
    }

    @Override
    public SseEmitter createNewEmitter() {
        Long userId = getCurrentUser().getId();
        if (emitterMap.containsKey(userId)) {
            return emitterMap.get(userId);
        }
        SseEmitter sseEmitter = new SseEmitter(TIMEOUT);
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
    public void sendNotification(EventType eventType, String destinationId, Long[] usersId) {
        for (Long userId : usersId) {
            sendNotification(eventType, destinationId, userId);
        }
    }

    @Override
    public void sendNotification(NotificationQueueDTO notificationQueueDTO) {
        EventType eventType = Enum.valueOf(EventType.class, notificationQueueDTO.getEventType());
        String destinationId = notificationQueueDTO.getDestinationId();
        Long userId = notificationQueueDTO.getUserId();
        sendNotification(eventType, destinationId, userId);
    }

    private void sendNotification(EventType eventType, String destinationId, Long userId) {
        try {
            if (emitterMap.containsKey(userId)) {
                emitterMap.get(userId).send(SseEmitter.event().name(eventType.name()).data(destinationId).build());
            }
        } catch (IOException e) {
            log.debug("Emitter send event failed for id: " + userId + " and event: " + eventType);
        }
    }
}

