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
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
     * 缓存线程池
     */
    private final ExecutorService executor = new ThreadPoolExecutor(4, 64,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());


    /**
     * 基于发布订阅模式 往中介发送主题消息
     *
     * @param topic    订阅主题
     * @param qos      消息接收的质量
     * @param content  消息内容
     * @param callback 回调函数
     */
    public void send(String topic, QualityOfServiceEnum qos, String content, MqttCallback callback) {
        // assemble message
        MqttMessage message = new MqttMessage(content.getBytes(StandardCharsets.UTF_8));
        message.setQos(qos.getCode());
        send(topic, message, callback);
    }

    /**
     * 基于发布订阅模式 往中介发送主题消息
     *
     * @param topic    订阅主题
     * @param message  消息体
     * @param callback 回调函数
     */
    public void send(String topic, MqttMessage message, MqttCallback callback) {
        if (callback != null) {
            client.setCallback(callback);
        }
        // submit task
        executor.submit(() -> {
            try {
                client.publish(topic, message);
                log.info("The client ({}) send topic ({}) message to server", client.getClientId(), topic);
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        });
    }
}