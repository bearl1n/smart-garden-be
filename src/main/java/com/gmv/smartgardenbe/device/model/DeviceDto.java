package com.gmv.smartgardenbe.device.model;

import com.gmv.smartgardenbe.entity.DeviceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    Long id;
    DeviceType deviceType;
    String inTopicName;
    String deviceName;
    String serialNumber;
    boolean isActive;
    List<RelayDto> relayList;

}


