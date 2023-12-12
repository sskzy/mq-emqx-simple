package com.example.demo;

import com.example.demo.emqx.message.impl.WildCardMessage;
import com.example.demo.emqx.subscribe.emun.SubscribeEnum;
import com.example.demo.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DemoApplicationTests {

    @Resource
    UserService userService;

    @Test
    void contextLoad() {
        userService.login();
    }

    @Test
    void contextLoad0() {
        System.out.println(SubscribeEnum.SHARE+"/asdf");
    }

}
