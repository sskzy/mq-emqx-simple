package com.example.demo.emqx.actuator;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EMQXClient {

    @Resource
    private MqttClient client;

    public void send(String subTopic, String pubTopic, Integer qos, String content) {
        try {
            client.subscribe(subTopic);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            client.publish(pubTopic, message);
            client.disconnect();
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}