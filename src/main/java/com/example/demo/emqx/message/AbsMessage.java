package com.example.demo.emqx.message;

import com.example.demo.emqx.actuator.EMQXActuator;
import com.example.demo.emqx.emun.QualityOfServiceEnum;

import javax.annotation.Resource;

/**
 * @author : songtc
 * @detail : 抽象消息类 消息发送的基类
 * @since : 2023/12/11 10:20
 */
public abstract class AbsMessage {
    @Resource
    EMQXActuator actuator;

    public void sendMostOnce(String topic, String content) {
        actuator.send(topic, QualityOfServiceEnum.QoS0, content);
    }

    public void sendLastOnce(String topic, String content) {
        actuator.send(topic, QualityOfServiceEnum.QoS1, content);
    }

    public void sendOnlyOnes(String topic, String content) {
        actuator.send(topic, QualityOfServiceEnum.QoS2, content);
    }
}
