package com.example.activemq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.activemq.conf.MQTTConfig.MQTTGateway;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/mqtt")
@AllArgsConstructor
public class MQTTProducerController {
    
    private final MQTTGateway mqttGateway;

    //http://localhost:8088/mqtt?m=im%20success
    @GetMapping
    public String send(@RequestParam String m) {
      mqttGateway.sendToMqtt(m);
      return "Message sent: "+m;
    }

}
