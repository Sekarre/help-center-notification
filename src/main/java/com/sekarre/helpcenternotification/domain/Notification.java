package com.sekarre.helpcenternotification.domain;

import com.sekarre.helpcenternotification.domain.enums.EventType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private Long userId;

    private String destinationId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    private boolean read = false;
}
