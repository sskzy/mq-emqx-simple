package com.example.demo.emqx.message;

import com.example.demo.emqx.message.TempleMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * 延迟消息 用户配置的时间间隔延迟发布消息
 *
 * @author : songtc
 * @since : 2023/12/11 10:21
 */
@Service
public class DelayMessage extends TempleMessage {
    public static final String RETAIN_KEYWORD = "$delayed/";

    /**
     * 发送延迟消息
     *
     * @param topic     订阅主题
     * @param content   消息内容
     * @param delayTime 单位是秒 允许的最大间隔是 4294967
     */
    public void send(String topic, String content, Long delayTime) {
        Assert.isTrue(delayTime < 0, "delay time value cannot be less than 0");
        Assert.isTrue(delayTime > 4294967, "delay time value is exceeded threshold");
        topic = RETAIN_KEYWORD + TimeUnit.SECONDS.toSeconds(delayTime) + "/" + topic;
        super.send(topic, content);
    }
}
