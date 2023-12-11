package com.example.demo.emqx.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MessageCallback implements MqttCallback {
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        System.out.println("连接断开，可以做重连");
    }

    public void messageArrived(String topic, MqttMessage message) {
        // subscribe后得到的消息会执行到这里面
        System.out.println("topic: " + topic);
        System.out.println("Qos: " + message.getQos());
        System.out.println("message: " + new String(message.getPayload()));
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("iMqttDeliveryToken: " + token.isComplete());
    }
}