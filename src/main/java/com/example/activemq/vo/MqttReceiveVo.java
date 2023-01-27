package com.example.activemq.vo;

import lombok.Data;

@Data
public class MqttReceiveVo {
    DeviceVo payload;
    MqttHeadersVo headers;
}
