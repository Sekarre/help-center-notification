package com.sekarre.helpcenternotification.DTO;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;


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
    private String createdAt;
}
