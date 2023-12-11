package com.example.demo.emqx.engine;

import com.example.demo.emqx.annotation.EMQX;
import com.example.demo.emqx.annotation.EMQXListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author : songtc
 * @detail : 消息发送的引擎 注入主题订阅和主题比对
 * @since : 2023/12/11 10:30
 */
@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EMQXEngine implements ApplicationContextAware {
    @Resource
    MqttClient client;
    @Resource
    ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> map = context.getBeansWithAnnotation(EMQX.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            try {
                for (Method method : clazz.getDeclaredMethods()) {
                    client.subscribe(method.getDeclaredAnnotation(EMQXListener.class).topic());
                    log.info("Class topic item subscribe finish ({})", method.getDeclaredAnnotation(EMQXListener.class).topic());
                }
                log.info("Class topic subscribe finish {}", clazz.getName());
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 初始化注解获取和字符串(订阅)处理
     */
}
