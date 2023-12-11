package com.example.demo.emqx.callback;

import com.example.demo.emqx.annotation.EMQX;
import com.example.demo.emqx.annotation.EMQXListener;
import com.example.demo.emqx.engine.EMQXEngine;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author : songtc
 * @detail : 消息发送的回调
 * @since : 2023/12/09 15:48
 */
public class MessageCallback implements MqttCallback {
    @Resource
    EMQXEngine emqxEngine;

    public MessageCallback(EMQXEngine emqxEngine) {
        this.emqxEngine = emqxEngine;
    }

    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        System.out.println("songtc 连接断开 可以做重连");
    }

    /**
     *
     * @param topic name of the topic on the message was published to
     * @param message the actual message.
     */
    public void messageArrived(String topic, MqttMessage message) {
        // 调用@Listener注解对应方法
        emqxEngine.invokeListener(topic, message);
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("iMqttDeliveryToken: " + token.isComplete());
    }
}