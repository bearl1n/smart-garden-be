package com.gmv.smartgardenbe.device;

import com.gmv.smartgardenbe.entity.Device;
import com.gmv.smartgardenbe.entity.DeviceType;

import java.util.List;

public interface DeviceFactory {

    DeviceType getDeviceType();
    Device getNewDevice();
    List<Device> getNewDevices(int count);

}

