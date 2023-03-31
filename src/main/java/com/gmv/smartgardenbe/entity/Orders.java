package com.gmv.smartgardenbe.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long deviceId;
    private Long relayId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private boolean relayStatus;

    @CreatedDate
    private LocalDateTime starDate;

    private LocalDateTime endDate;

}
