package com.gmv.smartgardenbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private DeviceType deviceType;

    /** mqtt topic which listening Iot device **/
    private String inTopicName;
    private String deviceName;
    private String serialNumber;
    private boolean isActive;
    @OneToMany(mappedBy="device", cascade = CascadeType.ALL)
    private List<Relay> relayList;
}
