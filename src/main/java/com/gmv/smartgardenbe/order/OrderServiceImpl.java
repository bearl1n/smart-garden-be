package com.gmv.smartgardenbe.order;

import com.gmv.smartgardenbe.entity.Device;
import com.gmv.smartgardenbe.entity.OrderStatus;
import com.gmv.smartgardenbe.entity.Orders;
import com.gmv.smartgardenbe.entity.Relay;
import com.gmv.smartgardenbe.mqtt.MqttProducer;
import com.gmv.smartgardenbe.mqtt.model.MqttMessage;
import com.gmv.smartgardenbe.repository.DeviceRepository;
import com.gmv.smartgardenbe.repository.OrderRepository;
import com.gmv.smartgardenbe.repository.RelayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Qualifier("OrderProcessing")
    private  final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final OrderRepository orderRepository;

    private final RelayRepository relayRepository;
    private final DeviceRepository deviceRepository;

    private final MqttProducer mqttProducer;

    @Scheduled(fixedDelay = 30 , timeUnit = TimeUnit.SECONDS)
    @Transactional
    public void handleOrder() {
        System.out.println("sheduler");
        List<Orders> newOrders = orderRepository.findNewOrders(OrderStatus.IN_ORDER.name(), LocalDateTime.now());
        log.info("neworders count: {}", newOrders.size());
        if(!newOrders.isEmpty()){
            newOrders.forEach(orders -> {
                orders.setOrderStatus(OrderStatus.IN_PROCESS);
                threadPoolTaskExecutor.execute(() -> processing(orders));
                orderRepository.saveAndFlush(orders);
                }
            );
        }
    }

    @Override
    public void processing(Orders orders) {
        Device device = deviceRepository.getById(orders.getDeviceId());
        log.info("deivice found: {}", device.getDeviceName());
        Relay relay = relayRepository.getById(orders.getRelayId());
        log.info("relay found: {}", relay.getName());
        String topic = device.getInTopicName();
        mqttProducer.send(topic, MqttMessage.builder()
                .relay(relay.getNumber())
                .on(orders.isRelayStatus() ? 1 : 0)
                .id(orders.getId().toString())
                .build());
    }


    @Override
    public void updateStatus(Long orderId) {
        log.info("Order with id: {}, completed", orderId);
        Orders orders = orderRepository.getById(orderId);
        orders.setOrderStatus(OrderStatus.SUCCESS);
        orders.setEndDate(LocalDateTime.now());
        orderRepository.saveAndFlush(orders);

        Relay relay = relayRepository.getById(orders.getRelayId());
        relay.setEnabled(orders.isRelayStatus());
        relayRepository.save(relay);
    }
}
