package com.example.demo.emqx.engine;

import com.example.demo.emqx.annotation.EMQX;
import com.example.demo.emqx.annotation.EMQXListener;
import com.example.demo.emqx.callback.MessageCallback;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     *
     */
    private Map<String, Object> clazzs;
    /**
     *
     */
    private Map<Object, List<String>> topics = new HashMap<>();
    /**
     *
     */
    private Map<String, Method> methods = new HashMap<>();

    /**
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        clazzs = context.getBeansWithAnnotation(EMQX.class);
        //
        for (Map.Entry<String, Object> entry : clazzs.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            List<String> list = new ArrayList<>();
            try {
                for (Method method : clazz.getDeclaredMethods()) {
                    String topic = method.getDeclaredAnnotation(EMQXListener.class).topic();
                    client.subscribe(topic);
                    methods.put(topic, method);
                    list.add(topic);
//                    log.info("Class topic item subscribe finish ({})", topic);
                }
                topics.put(clazz, list);
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
//            log.info("Class topic subscribe finish ({})", clazz.getName());
        }
        // delay set callback
        client.setCallback(new MessageCallback(this));
        log.info("Client all topic subscribe finish ({})", this.getClass().getName());
    }

    /**
     * @param topic
     * @param message
     */
    public void invokeListener(String topic, MqttMessage message) {
        for (Map.Entry<String, Object> entry : clazzs.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            // compare topic value is same
            List<Method> wildCardMethod = getWildCardMethod(clazz, topic);
            try {
                for (Method method : wildCardMethod) {
                    method.invoke(context.getBean(clazz), new String(message.getPayload()));
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
    public List<String> getWildCardTopics(Object clazz, String topic) {
        String[] split = topic.split(MqttTopic.TOPIC_LEVEL_SEPARATOR);
        return topics.get(clazz).stream()
                .filter(x -> x.startsWith(split[0])) // level 1 filtration
                .filter(x -> MqttTopic.isMatched(x, topic)) // general filtration
                .collect(Collectors.toList());
    }

    /**
     * @param clazz
     * @param topic
     * @return
     */
    public List<Method> getWildCardMethod(Object clazz, String topic) {
        List<Method> list = new ArrayList<>();
        for (String wildCardTopic : getWildCardTopics(clazz, topic)) {
            list.add(methods.get(wildCardTopic));
        }
        return list;
    }
}
