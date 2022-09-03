package com.sekarre.helpcenternotification.domain;

import com.sekarre.helpcenternotification.domain.enums.EventType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class NotificationLimiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinationId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private Long userId;
}
