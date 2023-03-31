package com.gmv.smartgardenbe.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmv.smartgardenbe.mqtt.model.MqttResponse;
import com.gmv.smartgardenbe.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MqttConsumer {

    private final ObjectMapper objectMapper;

    private final OrderService orderService;

    @Bean
    public IntegrationFlow inboundFlow (MqttPahoMessageDrivenChannelAdapter inboundAdapter ) {
        return IntegrationFlow.from(inboundAdapter)
                .handle((GenericHandler<String>) (payload, headers) -> {
                    System.out.println("new message! " + payload);
                    headers.forEach((k,v) -> System.out.println(k + "=" +v));
                    try {
                       MqttResponse mqttResponse =  objectMapper.readValue(payload, MqttResponse.class);
                       orderService.updateStatus(mqttResponse.getId());
                    } catch (JsonProcessingException e) {
                        log.error("Parsing exception :{}", e.getLocalizedMessage());
                        throw new RuntimeException(e);
                    }
                    return null;
                }).get();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter inboundAdpter(
            @Value("${mqtt.consumer.defaultTopic}") String topic,
            MqttPahoClientFactory clientFactory) {
        return new MqttPahoMessageDrivenChannelAdapter("consumer", clientFactory, topic);
    }

}
