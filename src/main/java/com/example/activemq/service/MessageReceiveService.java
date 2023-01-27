package com.example.activemq.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.activemq.vo.DeviceVo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class MessageReceiveService {

    public void getMessge(Object obj){
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES ).create();       
        String jsonStr = gson.toJson(obj);
        Map map = gson.fromJson(jsonStr, Map.class);
        DeviceVo device = gson.fromJson((String)map.get("payload"), DeviceVo.class);
                
        System.out.println(device);
    }
}
