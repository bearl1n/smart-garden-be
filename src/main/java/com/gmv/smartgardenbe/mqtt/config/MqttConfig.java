package com.gmv.smartgardenbe.mqtt.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;

@Slf4j
@Configuration
public class MqttConfig {
    @Bean
    public MqttPahoClientFactory clientFactory(
            @Value("${mqtt.host.uri}") String host,
            @Value("${mqtt.username}") String userName,
            @Value("${mqtt.password}") String pwd
    ) {
        log.debug("Create MQTT client factory, with uri: {}, username: {}, password: {}", host, userName, pwd);
        var factory = new DefaultMqttPahoClientFactory();
        var options = new MqttConnectOptions();
        options.setUserName(userName);
        options.setPassword(pwd.toCharArray());
        options.setServerURIs(new String[] { host });
        factory.setConnectionOptions(options);
        return factory;
    }



    @Bean
    public MqttPahoMessageHandler outboundAdapter(
            @Value("${mqtt.producer.defaultTopic}") String topic,
            MqttPahoClientFactory factory) {
        var mh = new MqttPahoMessageHandler("producer", factory);
        mh.setDefaultTopic(topic);
        return mh;
    }

    @Bean
    public IntegrationFlow outboundFlow(MessageChannel out, MqttPahoMessageHandler outboundAdapter) {
        return IntegrationFlow.from(out).handle(outboundAdapter).get();
    }

    @Bean
    public MessageChannel out() {
        return MessageChannels.direct().get();
    }
}

