package com.example.demo.emqx.subscribe.emun;

import lombok.Getter;

/**
 * @author : songtc
 * @since : 2023/12/11 10:29
 */
@Getter
public enum SubscribeEnum {

    /**
     * 共享订阅
     *
     * @link {<a href="https://www.emqx.io/docs/zh/v5.2/messaging/mqtt-shared-subscription.html">共享订阅</a>}
     */
    SHARE(1, "$share"),
    /**
     * 排它订阅
     *
     * @link {<a href="https://www.emqx.io/docs/zh/v5.2/messaging/mqtt-exclusive-subscription.html">排它订阅</a>}
     */
    EXCLUSIVE(2, "$exclusive");

    private final Integer code;
    private final String mean;

    SubscribeEnum(Integer code, String mean) {
        this.code = code;
        this.mean = mean;
    }

    @Override
    public String toString() {
        return mean;
    }
}
