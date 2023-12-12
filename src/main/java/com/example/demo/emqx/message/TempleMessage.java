package com.example.demo.emqx.message;

import com.example.demo.emqx.actuator.EMQXActuator;
import com.example.demo.emqx.emun.QualityOfServiceEnum;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.annotation.Resource;

/**
 * 抽象消息类 消息发送的基类
 *
 * @author : songtc
 * @since : 2023/12/11 10:20
 */
public abstract class TempleMessage {
    @Resource
    private EMQXActuator actuator;

    public void send(String topic, MqttMessage message) {
        actuator.send(topic, message, null);
    }

    public void send(String topic, MqttMessage message, MqttCallback callback) {
        actuator.send(topic, message, callback);
    }

    public void send(String topic, String content) {
        send(topic, content, QualityOfServiceEnum.QoS0, null);
    }

    public void send(String topic, String content, QualityOfServiceEnum qos) {
        send(topic, content, qos, null);
    }

    public void send(String topic, String content, QualityOfServiceEnum qos, MqttCallback mqttCallback) {
        actuator.send(topic, qos, content, mqttCallback);
    }
}
