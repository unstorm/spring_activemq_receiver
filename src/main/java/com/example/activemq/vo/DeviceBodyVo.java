package com.example.activemq.vo;

import lombok.Data;

@Data
public class DeviceBodyVo {
    String commandType; 
    PlcSprayControlVo plcSprayControl;
}
