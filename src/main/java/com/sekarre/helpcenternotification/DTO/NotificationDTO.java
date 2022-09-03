package com.sekarre.helpcenternotification.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.helpcenternotification.domain.enums.EventType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.sekarre.helpcenternotification.util.DateUtil.DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class NotificationDTO implements Serializable {

    private Long id;
    private Long userId;
    private String message;
    private String destinationId;
    private EventType eventType;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
}
