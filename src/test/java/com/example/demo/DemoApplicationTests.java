package com.example.demo;

import com.example.demo.emqx.engine.EMQXEngine;
import com.example.demo.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Resource
    UserService userService;

    @Test
    void contextLoad() {
        userService.login();
    }
}
