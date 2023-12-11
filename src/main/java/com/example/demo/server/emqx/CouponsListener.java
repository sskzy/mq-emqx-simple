package com.example.demo.server.emqx;

import com.example.demo.emqx.annotation.EMQXListener;
import org.springframework.stereotype.Component;

/**
 * @author : songtc
 * @since : 2023/12/09 15:07
 */
@Component
public class CouponsListener {

    public static final String CouponsTopic = "CouponsTopic";

    @EMQXListener(topic = CouponsTopic)
    public void coupons(String json) {
        // 设置优惠卷到期通知逻辑
    }
}
