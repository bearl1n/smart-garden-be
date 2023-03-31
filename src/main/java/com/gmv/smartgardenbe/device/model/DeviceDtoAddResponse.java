package com.gmv.smartgardenbe.device.model;

import lombok.Builder;

import java.util.List;

@Builder
public record DeviceDtoAddResponse(
        Long id,
        String serialNumber,
        String type,
        String name,
        List<RelayDto> relays
) {}
