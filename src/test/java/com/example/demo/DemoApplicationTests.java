package com.example.demo;

import com.example.demo.emqx.server.MqttClientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DemoApplicationTests {

    @Resource
    private MqttClientService mqttClientService;

    @Test
    void contextLoads() {
        mqttClientService.send("testtopic/#", "testtopic/1", 2, "Hello World");
    }


}
