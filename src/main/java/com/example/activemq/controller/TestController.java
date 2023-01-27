package com.example.activemq.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.activemq.vo.DeviceVo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class TestController {
    
    @RequestMapping("/")  
    public String hello() {        
        return "Hello my Activemq";  
    }  

    @RequestMapping("/test")
    public String test(){
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        DeviceVo device = gson.fromJson("{\"device_no\":\"01241191055\",\"message_type\":\"command\",\"message_id\":653400138,\"body\":{\"command_type\":\"plc_spray_control\",\"plc_spray_control\":{\"motor_select\":\"1\",\"spray_line\":\"1\",\"spray_time\":\"1\",\"dwell_time\":\"1\",\"repeat_number\":\"1\"}},\"expired_time\":\"20230127151013\",\"messaged_at\":\"2023-01-27 15:00:13\"}", DeviceVo.class);
        System.out.println(device);
        return device.getDeviceNo();
    }
}
