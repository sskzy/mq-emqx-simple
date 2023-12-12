package com.example.demo.emqx.emun;

import lombok.Getter;

/**
 * Qos的枚举 消息接收的质量
 *
 * @author : songtc
 * @since : 2023/12/09 14:37
 */
@Getter
public enum QualityOfServiceEnum {
    QoS0(0, "最多一次"),
    QoS1(1, "至少一次"),
    QoS2(2, "仅一次");

    private final Integer code;
    private final String mean;

    QualityOfServiceEnum(Integer code, String mean) {
        this.code = code;
        this.mean = mean;
    }

    @Override
    public String toString() {
        return code.toString();
    }
}
