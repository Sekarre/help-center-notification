package com.sekarre.helpcenternotification.domain;


import com.sekarre.helpcenternotification.domain.enums.RoleName;
import com.sekarre.helpcenternotification.domain.enums.Specialization;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;
}
