package com.example.activemq.vo;

import lombok.Data;

@Data
public class DeviceVo {
    String deviceNo;
    String messageType;
    Integer messageId;
    DeviceBodyVo body;
    String expiredTime;
    String messagedAt;
}
