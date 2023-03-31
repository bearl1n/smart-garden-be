package com.gmv.smartgardenbe.device;

import com.gmv.smartgardenbe.entity.DeviceHistory;

import java.util.List;

public interface DeviceHistoryService {

    void update(Long deviceId, Long userId);

    void delete(Long deviceId, Long userId);

    List<Long> getAllDevicesByUserId(Long userId);

    DeviceHistory getDeviceHistoryByIdAndUserId(Long deviceId, Long userId);
}
