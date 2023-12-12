package com.example.demo.emqx.message.impl;

import com.example.demo.emqx.message.TempleMessage;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author : songtc
 * @since : 2023/12/11 10:23
 */
@Service
public class RetainMessage extends TempleMessage {

    /**
     *
     * @param topic
     * @param content
     */
    public void send(String topic, String content) {
        MqttMessage mqttMessage = new MqttMessage(content.getBytes(StandardCharsets.UTF_8));
        // set message retained
        mqttMessage.setRetained(true);
        super.send(topic, mqttMessage);
    }

}
