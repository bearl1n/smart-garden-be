package com.gmv.smartgardenbe.device;

import com.gmv.smartgardenbe.auth.user.User;
import com.gmv.smartgardenbe.device.model.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@RestController
@RequestMapping("/api/v1/device")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/add/user/link", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceDto> addDeviceUserLink(@RequestBody DeviceUserLinkRequest request) {
        if (!hasText(request.getSerialNumber())) {
            throw new RuntimeException("serial number is null ");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        DeviceDto device = deviceService.addDevice(request.getSerialNumber(), user.getId().longValue());
        return ResponseEntity.ok().body(device);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteDevice(@RequestBody DeviceDtoRemoveRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        deviceService.deleteDevice(request.deviceId(), user.getId().longValue());
        return ResponseEntity.accepted().build();

    }


    @CrossOrigin(origins = "http://localhost:3000")
    @SecurityRequirement(name = "JWT")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceDtoResponseList> getDevices() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<DeviceDto> deviceDto = deviceService.fetchDevices(user.getId().longValue());
        return ResponseEntity.ok().body(DeviceDtoResponseList.builder().devices(deviceDto).build());

    }

    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/create")
    public ResponseEntity<DeviceDtoResponseList> create(@RequestBody @Parameter(description = "numbers of devices") Integer count) {
        List<DeviceDto> deviceList = deviceService.createNewDevice(count);
        return ResponseEntity.ok().body(DeviceDtoResponseList.builder().devices(deviceList).build());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @SecurityRequirement(name = "JWT")
    @PostMapping(value = "/{deviceId}/relay/{relayId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> relay(
            @PathVariable("deviceId") @Parameter(description = "Identificator devices") Long deviceId,
            @PathVariable("relayId") @Parameter(description = "Identificator relay") Long relayId,
            @RequestBody RelayDto relay
    ) {
        //TODO
        System.out.println("device:"+ deviceId + " relay:" +relayId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        deviceService.onOfRelay(deviceId, relayId, relay.isStatus(), user.getId().longValue());

        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @SecurityRequirement(name = "JWT")
    @GetMapping(value = "/{deviceId}/relay/{relayId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> findActiveOrder(
            @PathVariable("deviceId") @Parameter(description = "Identificator devices") Long deviceId,
            @PathVariable("relayId") @Parameter(description = "Identificator relay") Long relayId
    ) {
        System.out.println("device:"+ deviceId + " relay:" +relayId);
        OrderDto order = deviceService.findActiveOrder(deviceId, relayId);
        return ResponseEntity.ok().body(order);
    }

}
