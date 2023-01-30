package com.example.activemq.conf;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.example.activemq.service.MessageReceiveService;

@Configuration
public class MQTTConfig {
 
  @Autowired
  private MessageReceiveService receiveService;

  @Bean
  public MqttPahoClientFactory mqttClientFactory() {
      DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
      MqttConnectOptions options = new MqttConnectOptions();
      options.setServerURIs(new String[]{"tcp://192.168.32.129:1883"});
      options.setUserName("admin");
      options.setPassword("admin".toCharArray());
      factory.setConnectionOptions(options);

      return factory;
  }

  @Bean
  @ServiceActivator(inputChannel = "mqttOutboundChannel")
  public MessageHandler mqttOutbound() {
    MqttPahoMessageHandler messageHandler =
            new MqttPahoMessageHandler("javaTestClient", mqttClientFactory());
    messageHandler.setAsync(true);
    messageHandler.setDefaultTopic("antiice.mqtt.test");
    messageHandler.setDefaultQos(1);
    return messageHandler;
  }

  @Bean
  public MessageChannel mqttOutboundChannel() {
    return new DirectChannel();
  }

  @Bean
  public IntegrationFlow mqttInFlow() {
    return IntegrationFlows.from(mqttInbound())
            .transform(p -> p + ", received from MQTT")
            .handle(message -> System.out.println("MQTT Message received: " + message))
            .get();
  }

  @Bean
  public IntegrationFlow mqttSprayInFlow() {
    return IntegrationFlows.from(mqttSprayInbound())
            //.transform(p -> p + ", received spray from MQTT")           
            //.handle(message -> System.out.println("MQTT Message spray received: " + message))
            .handle(message -> receiveService.getMessge(message))
            .get();
  }

  @Bean
  public MessageProducerSupport mqttInbound() {
    MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("javaMqqtClient",
            mqttClientFactory(), "antiice.mqtt.test");
    adapter.setCompletionTimeout(5000);
    adapter.setConverter(new DefaultPahoMessageConverter());
    adapter.setQos(1);
    return adapter;
  }
  
  @Bean
  public MessageProducerSupport mqttSprayInbound() {
    MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("javaMqqtSprayClient",
            mqttClientFactory(), "seda/server/01241191055");
    adapter.setCompletionTimeout(5000);
    adapter.setConverter(new DefaultPahoMessageConverter());
    adapter.setQos(1);
    return adapter;
  }

  @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
  public interface MQTTGateway {
    void sendToMqtt(String data);
  }
}
