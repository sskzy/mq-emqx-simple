package com.example.demo.server.config;

import com.example.demo.emqx.message.WillMessage;
import com.example.demo.emqx.emun.QualityOfServiceEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : songtc
 * @since : 2023/12/12 12:21
 */
@Configuration
public class WillConfig {
    @Bean
    public WillMessage willMessage() {
        WillMessage willMessage = new WillMessage();
        willMessage.setTopic("lostBean");
        willMessage.setContent("bye bye");
        willMessage.setQos(QualityOfServiceEnum.QoS0);
        willMessage.setRetained(true);
        return willMessage;
    }

}
