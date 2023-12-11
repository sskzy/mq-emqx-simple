package com.example.demo.emqx.message.impl;

import com.example.demo.emqx.message.AbsMessage;
import org.springframework.stereotype.Service;

/**
 * @author : songtc
 * @detail : 通配符消息 用户实际注入使用的服务
 * @since : 2023/12/11 10:25
 */
@Service
public class WildCardMessage extends AbsMessage {

    @Override
    public void sendMostOnce(String topic, String content) {
        super.sendMostOnce(topic, content);
    }

    @Override
    public void sendLastOnce(String topic, String content) {
        super.sendLastOnce(topic, content);
    }

    @Override
    public void sendOnlyOnes(String topic, String content) {
        super.sendOnlyOnes(topic, content);
    }
}
