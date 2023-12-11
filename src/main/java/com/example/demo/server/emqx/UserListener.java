package com.example.demo.server.emqx;

import com.example.demo.emqx.annotation.EMQX;
import com.example.demo.emqx.annotation.EMQXListener;
import org.springframework.stereotype.Component;

/**
 * @author : songtc
 * @since : 2023/12/09 15:07
 */
@EMQX
@Component
public class UserListener {

    public static final String CouponsTopic = "CouponsTopic";

    @EMQXListener(topic = CouponsTopic + "/g1")
    public void coupons1(String json) {
        // 设置优惠卷到期通知逻辑
    }

    @EMQXListener(topic = CouponsTopic + "/g2")
    public void coupons2(String json) {
        // 设置优惠卷到期通知逻辑
    }
}
