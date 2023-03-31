package com.gmv.smartgardenbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device_history")
public class DeviceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long versionNumber;

    private Long deviceId;

    private Long userId;

    private LocalDateTime starDate;

    private LocalDateTime endDate;

    @PrePersist
    public void setStartDataTime() {
        this.starDate = LocalDateTime.now();
    }

}
