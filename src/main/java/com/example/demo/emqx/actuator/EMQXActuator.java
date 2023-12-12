package com.example.demo.emqx.actuator;


import com.example.demo.emqx.emun.QualityOfServiceEnum;
import com.example.demo.emqx.engine.EMQXEngine;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息发送的执行器 创建和执行实际的消息发送
 *
 * @author : songtc
 * @since : 2023/12/09 15:48
 */
@Slf4j
@Component
public class EMQXActuator {
    @Resource
    private MqttClient client;

    /**
     * @param topic
     * @param message
     * @param mqttCallback
     */
    public void send(String topic, MqttMessage message, MqttCallback mqttCallback) {
        try {
            client.publish(topic, message);
            log.info("");
//            client.disconnect();
//            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param topic
     * @param qos
     * @param content
     */
    public void send(String topic, QualityOfServiceEnum qos, String content, MqttCallback mqttCallback) {
        try {
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos.getCode());
            client.publish(topic, message);
            log.info("");
//            client.disconnect();
//            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}