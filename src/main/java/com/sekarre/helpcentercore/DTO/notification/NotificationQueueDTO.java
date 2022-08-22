package com.sekarre.helpcentercore.DTO.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.sekarre.helpcenternotification.util.DateUtil.DATE_TIME_FORMAT;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationQueueDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2650647051596909952L;

    private Long id;
    private Long userId;
    private String message;
    private String destinationId;
    private String eventType;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
}
