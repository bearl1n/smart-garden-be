package com.gmv.smartgardenbe.device;

import com.gmv.smartgardenbe.device.model.DeviceDto;
import com.gmv.smartgardenbe.device.model.OrderDto;
import com.gmv.smartgardenbe.entity.*;
import com.gmv.smartgardenbe.mapper.DevicesMapper;
import com.gmv.smartgardenbe.repository.DeviceRepository;
import com.gmv.smartgardenbe.repository.OrderRepository;
import com.gmv.smartgardenbe.repository.RelayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceHistoryService deviceHistoryService;

    private final DeviceFactory deviceFactory;

    private final RelayRepository relayRepository;

    private final DeviceRepository deviceRepository;


    private final OrderRepository orderRepository;


    @Transactional
    public DeviceDto addDevice(String serialNumber, Long userId) {
        log.info("Find Device By serialNumber:{}", serialNumber);
        Device device = deviceRepository.findBySerialNumberAndIsActiveFalse(serialNumber)
                .orElseThrow(() -> new RuntimeException("Serial number incorected"));
        log.info("Create RelationShip with user", serialNumber);

        device.setActive(true);
        deviceRepository.save(device);
        deviceHistoryService.update(device.getId(), userId);
        return DevicesMapper.mapToDeviceDto(device);

    }

    public List<DeviceDto> createNewDevice(Integer count) {
        log.info("Create new device, with templated parameters");
        List<Device> deviceList = deviceFactory.getNewDevices(count);
        log.info("Save devices in DataBase: {}", deviceList.size());
        deviceRepository.saveAll(deviceList);
        return DevicesMapper.mapToDeviceDtoList(deviceList);
    }

    public void deleteDevice(Long deviceId, Long userId) {
        deviceHistoryService.delete(deviceId, userId);
        Device device = deviceRepository.getReferenceById(deviceId);
        device.setActive(false);
        deviceRepository.save(device);
        log.info("Device with id:{} not active:{}", deviceId, device.isActive());
    }

    public List<DeviceDto> fetchDevices(Long userId) {
        List<Long> deviceIds = deviceHistoryService.getAllDevicesByUserId(userId);
        List<Device> devices = deviceRepository.findAllByIds(deviceIds);
        return DevicesMapper.mapToDeviceDtoList(devices);
    }

    public void onOfRelay(Long deviceId, Long relayId, boolean status, Long userId) {
        DeviceHistory deviceHistory = deviceHistoryService
                .getDeviceHistoryByIdAndUserId(deviceId, userId);
        Device device = deviceRepository.getReferenceById(deviceHistory.getDeviceId());
        log.info("deivice found: {}", device.getDeviceName());
        Relay relay = relayRepository.getReferenceById(relayId);
        log.info("relay found: {}", relay.getName());
        Orders orderId = orderRepository.saveAndFlush(Orders.builder()
                .deviceId(deviceId)
                .relayId(relayId)
                .relayStatus(status)
                .orderStatus(OrderStatus.IN_ORDER)
                .starDate(LocalDateTime.now())
                .build());
        log.debug("save order id:{}", orderId);
    }


    /**
     * Поиск последнего активного заказа
     * в случае если нашли более одного заказа, возвращаем последний созданный
     * @param deviceId - идентификатор устройства
     * @param relayId - идентификатор реле
     * @return информация о заказе
     */
    public OrderDto findActiveOrder(Long deviceId, Long relayId) {

        List<Orders> ordersList = orderRepository.findActiveOrder(deviceId, relayId);
        if (CollectionUtils.isEmpty(ordersList)) {
            return new OrderDto();
        }
        if (ordersList.size()==1) {
           Orders orders =  ordersList.get(0);
           return mapToOrderDto(orders);
        }
        ordersList.sort(Comparator.comparing(Orders::getStarDate));
        for (int i=0; i<ordersList.size()-1; i++) {
            Orders orders = ordersList.get(i);
            orders.setOrderStatus(OrderStatus.CLOSED);
            orders.setEndDate(LocalDateTime.now());
            orderRepository.save(orders);
        }
       return mapToOrderDto(ordersList.get(ordersList.size()-1));
    }


   public OrderDto mapToOrderDto(Orders orders) {
        OrderDto order = new OrderDto();
        if (orders == null) {
            return order;
        }
        if (orders.getId()!= null ){
            order.setId(orders.getId());
        }
       if (orders.getRelayId()!= null ){
           order.setRelayId(orders.getRelayId());
       }
       if (orders.getDeviceId()!= null ){
           order.setDeviceId(orders.getDeviceId());
       }
       if (orders.getOrderStatus()!= null ){
           order.setOrderStatus(orders.getOrderStatus().name());
       }
       if (orders.isRelayStatus() ){
           order.setRelayStatus(true);
       }
       if (orders.getStarDate()!= null ){
           order.setStarDate(orders.getStarDate());
       }
       if (orders.getEndDate()!= null ){
           order.setEndDate(orders.getEndDate());
       }
        return order;
    }
}
