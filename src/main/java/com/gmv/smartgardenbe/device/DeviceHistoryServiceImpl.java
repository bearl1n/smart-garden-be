package com.gmv.smartgardenbe.device;

import com.gmv.smartgardenbe.entity.DeviceHistory;
import com.gmv.smartgardenbe.repository.DeviceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceHistoryServiceImpl  implements  DeviceHistoryService {

    private final DeviceHistoryRepository deviceHistoryRepository;
    @Override
    public void update(Long deviceId, Long userId) {
        log.info("Find history records by deviceId: {} and userId: {}", deviceId, userId);
        Optional<DeviceHistory> deviceHistory = deviceHistoryRepository.findByDeviceIdAndEndDateIsNull(deviceId);
        if(deviceHistory.isPresent()) {
            throw new RuntimeException("Device already used");
        }
        deviceHistoryRepository.save(DeviceHistory.builder()
                .deviceId(deviceId)
                .userId(userId)
                .build());

    }

    @Override
    public void delete(Long deviceId, Long userId) {
        DeviceHistory device = getDeviceHistoryByIdAndUserId(deviceId, userId);
        device.setEndDate(LocalDateTime.now());
        deviceHistoryRepository.save(device);
        log.info("Deleted record in device history");
    }

    @Override
    public DeviceHistory getDeviceHistoryByIdAndUserId(Long deviceId, Long userId) {
        log.info("Find history records by deviceId: {} and userId: {}", deviceId, userId);
        Optional<DeviceHistory> deviceHistory = deviceHistoryRepository.findByDeviceIdAndUserIdAndEndDateIsNull(deviceId, userId);
        if(deviceHistory.isEmpty()) {
            final String msg = String.format("User with id: %d, not relationship with device: %d", userId, deviceId);
            throw new RuntimeException(msg);
        }
        DeviceHistory device = deviceHistory.get();
        return device;
    }

    @Override
    public List<Long> getAllDevicesByUserId(Long userId) {
        log.info("Find Devices by userId :{}", userId);
        List<DeviceHistory> deviceHistories =  deviceHistoryRepository.findAllByUserIdAndEndDateIsNull(userId);
        log.info("Found {} devices", deviceHistories.size());
        return deviceHistories.stream().map(DeviceHistory::getDeviceId).collect(Collectors.toList());
    }
}
