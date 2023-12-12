package com.example.demo.temple;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class App {
    public static void main(String[] args) {
        String pubTopic = "aa";
        String content = "Hello World3";
        int qos = 0;
        String broker = "tcp://127.0.0.1:1883";
        String clientId = "emqx_test";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);

            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName("admin");
            connOpts.setPassword("123456".toCharArray());
            // 保留会话
            connOpts.setCleanSession(true);
            client.setCallback(new OnMessageCallback());
            client.connect(connOpts);

            // 消息发布所需参数
            MqttMessage message = new MqttMessage(content.getBytes());

            message.setQos(qos);
            client.publish(pubTopic, message);

            client.disconnect();
            client.close();
            System.exit(0);

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}