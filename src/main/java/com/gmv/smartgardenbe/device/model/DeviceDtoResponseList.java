package com.gmv.smartgardenbe.device.model;

import lombok.Builder;

import java.util.List;

@Builder
public record DeviceDtoResponseList(
        List<DeviceDto> devices
) {}