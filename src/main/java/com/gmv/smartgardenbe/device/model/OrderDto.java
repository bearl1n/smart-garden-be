package com.gmv.smartgardenbe.device.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private Long deviceId;
    private Long relayId;
    private String orderStatus;
    private boolean relayStatus;
    private LocalDateTime starDate;
    private LocalDateTime endDate;
}