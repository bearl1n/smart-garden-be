package com.gmv.smartgardenbe.repository;

import com.gmv.smartgardenbe.entity.DeviceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceHistoryRepository extends JpaRepository<DeviceHistory, Long>{

    Optional<DeviceHistory> findByDeviceIdAndEndDateIsNull(Long deviceId);

    Optional<DeviceHistory> findByDeviceIdAndUserIdAndEndDateIsNull(Long deviceId, Long userId);

    List<DeviceHistory> findAllByUserIdAndEndDateIsNull(Long userId);
}

