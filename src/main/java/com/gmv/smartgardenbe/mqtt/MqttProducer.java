package com.gmv.smartgardenbe.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmv.smartgardenbe.mqtt.config.MqttConfig;
import com.gmv.smartgardenbe.mqtt.model.MqttMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MqttProducer {
    private final MqttConfig mqttConfig;
    private final ObjectMapper objectMapper;
    public void send(String topic, MqttMessage mqttMessage){
        try {
            String mqttMessageAsString = objectMapper.writeValueAsString(mqttMessage);
            var message = MessageBuilder.withPayload(mqttMessageAsString)
                    .setHeader(MqttHeaders.TOPIC, topic).build();
            mqttConfig.out().send(message);
            log.info("message sent in topic:{}", topic);
        } catch (JsonProcessingException e) {
            final String msg = "Error parsing mqttMessage";
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }


}
