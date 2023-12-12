package com.example.demo.emqx.engine;

import com.example.demo.emqx.annotation.EMQX;
import com.example.demo.emqx.annotation.EMQXListener;
import com.example.demo.emqx.callback.ListenerCallback;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : songtc
 * @detail : 消息发送的引擎 注入主题订阅和主题比对
 * @since : 2023/12/11 10:30
 */
@Slf4j
@Component
public class EMQXEngine implements ApplicationContextAware {
    @Resource
    MqttClient client;
    @Resource
    ApplicationContext context;

    /**
     * Map<对象,Map<主题,方法>>
     */
    private Map<Object, Map<String, Method>> clazzMap;

    /**
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        clazzMap = new HashMap<>();
        //
        for (Map.Entry<String, Object> entry : context.getBeansWithAnnotation(EMQX.class).entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            Map<String, Method> map = new HashMap<>();
            try {
                for (Method method : clazz.getDeclaredMethods()) {
                    String topic = method.getDeclaredAnnotation(EMQXListener.class).topic();
                    MqttTopic.validate(topic, true); // validate topic rules
                    client.subscribe(topic);
                    map.put(topic, method);
//                    log.info("Class topic item subscribe finish ({})", topic);
                }
                clazzMap.put(clazz, map);
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
//            log.info("Class topic subscribe finish ({})", clazz.getName());
        }
        // delay set callback
        client.setCallback(new ListenerCallback(this));
        log.info("Client all topic subscribe finish ({})", this.getClass().getName());
    }

    /**
     * @param topic
     * @param message
     */
    public void invokeListener(String topic, MqttMessage message) {
        for (Map.Entry<Object, Map<String, Method>> entry : clazzMap.entrySet()) {
            Object clazz = entry.getKey();
            // compare topic value is same
            Map<String, Method> wildCardTopics = getWildCardTopics(clazz, topic);
            try {
                for (Method method : wildCardTopics.values()) {
                    method.invoke(context.getBean((Class<?>) clazz), new String(message.getPayload()));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param topic
     * @return
     */
    public Map<String, Method> getWildCardTopics(Object clazz, String topic) {
        String[] split = topic.split(MqttTopic.TOPIC_LEVEL_SEPARATOR);
        return clazzMap.get(clazz).entrySet()
                .stream()
                .filter(x -> x.getKey().startsWith(split[0])) // level 1 filtration
                .filter(x -> MqttTopic.isMatched(x.getKey(), topic)) // general filtration
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
