package com.gmv.smartgardenbe.device;

import com.gmv.smartgardenbe.entity.Device;
import com.gmv.smartgardenbe.entity.DeviceType;
import com.gmv.smartgardenbe.entity.Relay;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class WaterDeviceFactory implements DeviceFactory {
    @Override
    public DeviceType getDeviceType() {
        return DeviceType.WATER_CONTROLLER;
    }

    @Override
    public Device getNewDevice() {
        List<Relay> relayList = List.of(
                Relay.builder().name("First relay").number(1).build(),
                Relay.builder().name("Second relay").number(2).build(),
                Relay.builder().name("Third relay").number(3).build(),
                Relay.builder().name("Fourth relay").number(4).build()
        );

        Device device = Device.builder()
                .deviceType(this.getDeviceType())
                .deviceName(UUID.randomUUID().toString())
                .serialNumber(UUID.randomUUID().toString())
                .inTopicName(UUID.randomUUID().toString())
                .build();
        relayList.forEach(relay -> relay.setDevice(device));
        device.setRelayList(relayList);
        return device;

    }

    @Override
    public List<Device> getNewDevices(int count) {
        List<Device> resultList = new ArrayList<>();
        while (count > 0) {
            resultList.add(getNewDevice());
            count--;
        }
        return resultList;
    }
}
