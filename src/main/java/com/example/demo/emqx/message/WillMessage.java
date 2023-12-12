package com.example.demo.emqx.message;

import com.example.demo.emqx.emun.QualityOfServiceEnum;
import lombok.Data;

/**
 * 遗嘱消息
 *
 * @author : songtc
 * @since : 2023/12/12 12:17
 */
@Data
public class WillMessage {
    /**
     * 订阅主题
     */
    private String topic;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息服务质量
     */
    private QualityOfServiceEnum qos = QualityOfServiceEnum.QoS0;
    /**
     * 是否保留
     */
    private Boolean retained = false;
}
