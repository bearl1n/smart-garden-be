package com.gmv.smartgardenbe.mqtt.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MqttMessage {
    /** Number relay in IOT  (1,2,3,4)*/
    private int relay;

    /** On - 1 , Off -0 */
    private int on;

    /** OrderId  UID */
    private String id;
}
