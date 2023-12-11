package com.example.demo.emqx.config;

import com.example.demo.emqx.callback.MessageCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : songtc
 * @since : 2023/12/09 14:16
 */
@Configuration
public class EMQXConfig {

    @Value("${emqx.broker}")
    private String broker;

    @Value("${emqx.username}")
    private String username;

    @Value("${emqx.password}")
    private String password;

    @Bean
    public MqttClient mqttClient() {
        MqttClient client = null;
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            client = new MqttClient(broker, "clientId", persistence);
            client.setCallback(new MessageCallback());
            client.connect(getConnectOptions());
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return client;
    }

    public MqttConnectOptions getConnectOptions() {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());
        connOpts.setCleanSession(true);
        return connOpts;
    }
}
