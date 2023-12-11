package com.example.demo.server.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.emqx.emun.QualityOfServiceEnum;
import com.example.demo.emqx.message.AbsMessage;
import com.example.demo.emqx.message.impl.WildCardMessage;
import com.example.demo.server.domain.User;
import com.example.demo.server.emqx.UserListener;
import com.example.demo.server.service.UserService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author : songtc
 * @since : 2023/12/09 15:08
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    WildCardMessage message;

    @Override
    public void login() {
        User userInfo = getUserInfo();
        // 发送设置优惠卷到期通知信息
        message.sendMostOnce(UserListener.CouponsTopic + "/" + userInfo.getId(),
                JSONObject.toJSONString(userInfo));
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
