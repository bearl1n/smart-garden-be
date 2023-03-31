package com.gmv.smartgardenbe.mapper;

import com.gmv.smartgardenbe.device.model.DeviceDto;
import com.gmv.smartgardenbe.device.model.RelayDto;
import com.gmv.smartgardenbe.entity.Device;
import com.gmv.smartgardenbe.entity.Relay;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DevicesMapper {

    public static DeviceDto mapToDeviceDto(Device device) {
        DeviceDto dto = new DeviceDto();

        if (device == null) {
            return dto;
        }
        if (Objects.nonNull(device.getId())) {
            dto.setId(device.getId());
        }
        if (Objects.nonNull(device.getDeviceType())) {
            dto.setDeviceType(device.getDeviceType());
        }
        if (Objects.nonNull(device.getInTopicName())) {
            dto.setInTopicName(device.getInTopicName());
        }
        if (Objects.nonNull(device.getDeviceName())) {
            dto.setDeviceName(device.getDeviceName());
        }
        if (Objects.nonNull(device.getSerialNumber())) {
            dto.setSerialNumber(device.getSerialNumber());
        }
        if (device.isActive()) {
            dto.setActive(true);
        }
        if (!CollectionUtils.isEmpty(device.getRelayList())) {
            dto.setRelayList(mapToRelayDtoList(device.getRelayList()));
        }
        return dto;
    }

    public static List<DeviceDto> mapToDeviceDtoList(List<Device> devicesList) {
        return devicesList.stream().map(DevicesMapper::mapToDeviceDto).collect(Collectors.toList());
    }

    public static List<RelayDto> mapToRelayDtoList(List<Relay> relays) {
        return relays.stream().map(DevicesMapper::mapToRelay).collect(Collectors.toList());
    }

    public static RelayDto mapToRelay(Relay relay) {
        RelayDto dto = new RelayDto();
        if (Objects.isNull(relay)) {
            return new RelayDto();
        }
        if (Objects.nonNull(relay.getId())) {
            dto.setId(relay.getId());
        }
        if (Objects.nonNull(relay.getName())) {
            dto.setName(relay.getName());
        }
        if (relay.isEnabled()) {
            dto.setStatus(true);
        }
        return dto;
    }
}

