package com.sekarre.helpcenternotification.DTO;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationLimiterQueueDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7089694046519699819L;

    private String destinationId;
    private String eventType;
    private Long userId;
    private boolean isLimited;
}
