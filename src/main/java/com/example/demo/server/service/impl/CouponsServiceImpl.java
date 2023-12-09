package com.example.demo.server.service.impl;

import com.example.demo.server.domain.User;
import com.example.demo.server.service.CouponsService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author : songtc
 * @since : 2023/12/09 15:08
 */
@Service
public class CouponsServiceImpl implements CouponsService {

    @Override
    public void login() {
        User userInfo = getUserInfo();
        // 设置优惠卷到期通知
        MqttMessage mqttMessage = new MqttMessage();
    }

    private User getUserInfo() {
        User user = new User();
        user.setId(1L);
        user.setUsername("小明");
        user.setPassword("123123");
        user.setCouponsList(new ArrayList<>() {{
            new User.Coupons(1L, "优惠卷1", LocalDateTime.now().plusMinutes(1L));
            new User.Coupons(2L, "优惠卷2", LocalDateTime.now().plusMinutes(2L));
        }});
        return user;
    }
}
