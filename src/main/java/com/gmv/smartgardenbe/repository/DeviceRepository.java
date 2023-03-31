package com.gmv.smartgardenbe.repository;

import com.gmv.smartgardenbe.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findBySerialNumberAndIsActiveFalse(String serialNumber);

    @Query(value = "select dev from Device dev where dev.id in :ids")
    List<Device> findAllByIds(@Param("ids") Iterable<Long> deviceIds);

    @Query(value = "select dev from Device dev where dev.id in :id")
    Device getById(@Param("id") Long deviceId);

}
