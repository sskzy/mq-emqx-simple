package com.example.demo.emqx.engine;

import com.example.demo.emqx.annotation.EMQX;
import com.example.demo.emqx.annotation.EMQXListener;
import com.example.demo.emqx.callback.MessageCallback;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.MethodInvoker;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

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
    private Map<String, Object> map;

    /**
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext)
            throws BeansException {
        map = context.getBeansWithAnnotation(EMQX.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            try {
                for (Method method : clazz.getDeclaredMethods()) {
                    String topic = method.getDeclaredAnnotation(EMQXListener.class).topic();
                    client.subscribe(topic);
//                    log.info("Class topic item subscribe finish ({})", topic);
                }
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
//            log.info("Class topic subscribe finish ({})", clazz.getName());
        }
        //
        client.setCallback(new MessageCallback(this));
        log.info("Client all topic subscribe finish ({})", this.getClass().getName());
    }

    /**
     * @param topic
     * @param message
     */
    public void invokeListener(String topic, MqttMessage message) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            for (Method method : clazz.getDeclaredMethods()) {
                // topic value is same
                if (topic.equals(method.getDeclaredAnnotation(EMQXListener.class).topic())) {
                    try {
                        method.invoke(context.getBean(clazz), new String(message.getPayload()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    void wildCardCompare() {

    }

}
