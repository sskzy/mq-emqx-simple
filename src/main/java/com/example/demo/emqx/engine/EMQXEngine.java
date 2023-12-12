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
 * 消息发送的引擎 注入主题订阅和主题比对
 *
 * @author : songtc
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
     * Map<EMQX对象,Map<Listener主题,Listener方法>> 监听映射表
     */
    private Map<Object, Map<String, Method>> clazzMap;

    /**
     * 获取 @EMQX修饰类的 @EMQXListener修饰的方法与主题 装配至 clazzMap
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        clazzMap = new HashMap<>();
        /* TODO 获取@EMQX修饰的类 后期可以优化为@Component或抛弃 同时@EMQX可以用作主题的用法(参考: @RequestMapping) */
        for (Map.Entry<String, Object> entry : context.getBeansWithAnnotation(EMQX.class).entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            Map<String, Method> map = new HashMap<>();
            try {
                for (Method method : clazz.getDeclaredMethods()) {
                    String topic = method.getDeclaredAnnotation(EMQXListener.class).topic();
                    MqttTopic.validate(topic, true); // validate topic rules
                    //
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
        // set listener callback
        client.setCallback(new ListenerCallback(this));
        log.info("Client all topic subscribe finish ({})", this.getClass().getName());
    }

    /**
     * 代理调用 @Listener注解标注的方法
     *
     * @param topic   订阅主题
     * @param message 消息体
     */
    public void invokeListener(String topic, MqttMessage message) {
        for (Map.Entry<Object, Map<String, Method>> entry : clazzMap.entrySet()) {
            Object clazz = entry.getKey();
            // compare topic value is same
            Map<String, Method> wildCardTopics = getWildCardTopics(clazz, topic);
            try {
                for (Method method : wildCardTopics.values()) {
                    // invoke @Listener mapping method
                    method.invoke(context.getBean((Class<?>) clazz), new String(message.getPayload()));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 从 clazzMap监听映射表中过滤出符合对应主题的对象
     *
     * @param topic 订阅主题
     * @return 符合对应主题的对象
     */
    private Map<String, Method> getWildCardTopics(Object clazz, String topic) {
        String[] split = topic.split(MqttTopic.TOPIC_LEVEL_SEPARATOR);
        //
        return clazzMap.get(clazz).entrySet()
                .stream()
                .filter(x -> x.getKey().startsWith(split[0])) // level 1 filtration
                .filter(x -> MqttTopic.isMatched(x.getKey(), topic)) // general filtration
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
