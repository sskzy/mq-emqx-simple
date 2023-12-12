package com.example.demo.emqx.callback;

import com.example.demo.emqx.engine.EMQXEngine;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.annotation.Resource;

/**
 * 消息发送回调 包含执行的监听器
 *
 * @author : songtc
 * @since : 2023/12/09 15:48
 */
public class ListenerCallback implements MqttCallback {
    @Resource
    EMQXEngine emqxEngine;

    public ListenerCallback(EMQXEngine emqxEngine) {
        this.emqxEngine = emqxEngine;
    }

    public void connectionLost(Throwable cause) {
        // 连接丢失后 一般在这里面进行重连
        System.out.println("conn reload");
    }

    /**
     * @param topic   name of the topic on the message was published to
     * @param message the actual message.
     */
    public void messageArrived(String topic, MqttMessage message) {
        // invoke @Listener mapping method
        emqxEngine.invokeListener(topic, message);
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("callback complete: " + token.isComplete());
    }
}