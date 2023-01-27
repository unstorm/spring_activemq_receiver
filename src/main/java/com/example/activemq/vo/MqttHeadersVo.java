package com.example.activemq.vo;

import lombok.Data;

@Data
public class MqttHeadersVo {
    String mqttReceivedRetained;
    Integer mqttId;
    String mqttDuplicate;
    String id;
    String mqttReceivedTopic;
    String mqttReceivedQos;
    Double timestamp;
}
