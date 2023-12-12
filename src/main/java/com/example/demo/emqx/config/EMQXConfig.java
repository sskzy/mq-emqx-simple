package com.example.demo.emqx.config;

import com.example.demo.emqx.message.WillMessage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * 配置类 读取和装配客户端
 *
 * @author : songtc
 * @since : 2023/12/09 14:16
 */
@Slf4j
@Configuration
public class EMQXConfig {

    @Value("${emqx.clientId}")
    private String clientId;

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
            client = new MqttClient(broker, clientId, persistence);
            client.connect(getConnectOptions());
            log.info("EMQX client is connect");
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Resource
    private WillMessage willMessage;

    /**
     * 装配 MqttClient的配置项
     */
    public MqttConnectOptions getConnectOptions() {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());
        if (willMessage != null) {
            connOpts.setWill(willMessage.getTopic(),
                    willMessage.getContent().getBytes(StandardCharsets.UTF_8),
                    willMessage.getQos().getCode(),
                    willMessage.getRetained());
            log.info("The client ({}) set topic({}) will message", clientId, willMessage.getTopic());
        }
        connOpts.setCleanSession(true);
        return connOpts;
    }
}
