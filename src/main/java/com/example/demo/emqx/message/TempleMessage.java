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

    /**
     * 发送消息
     *
     * @param topic   订阅主题
     * @param message 消息体
     */
    public void send(String topic, MqttMessage message) {
        actuator.send(topic, message, null);
    }

    /**
     * 发送消息
     *
     * @param topic    订阅主题
     * @param message  消息体
     * @param callback 回调函数
     */
    public void send(String topic, MqttMessage message, MqttCallback callback) {
        actuator.send(topic, message, callback);
    }

    /**
     * 发送消息
     *
     * @param topic   订阅主题
     * @param content 消息内容
     */
    public void send(String topic, String content) {
        send(topic, content, QualityOfServiceEnum.QoS0, null);
    }

    /**
     * 发送消息
     *
     * @param topic   订阅主题
     * @param content 消息内容
     * @param qos     消息服务质量
     */
    public void send(String topic, String content, QualityOfServiceEnum qos) {
        send(topic, content, qos, null);
    }

    /**
     * 发送消息
     *
     * @param topic    订阅主题
     * @param content  消息内容
     * @param qos      消息服务质量
     * @param callback 回调函数
     */
    public void send(String topic, String content, QualityOfServiceEnum qos, MqttCallback callback) {
        actuator.send(topic, qos, content, callback);
    }
}
