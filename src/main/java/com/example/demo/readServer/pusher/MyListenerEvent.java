package com.example.demo.readServer.pusher;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MyListenerEvent {

    @Async   // 异步方式
    @EventListener   // 监听器注解
    public void myListenerEvent(MyEvent event) {
        System.out.println("获取到的事件源 = " + event.getSource());
        System.out.println("注解方式--接收到MyEvent事件，发送短信/发送邮件");
    }

    @Async   // 异步方式
    @EventListener   // 监听器注解
    public void myListenerEvent2(MyEvent2 event) {
        System.out.println("获取到的事件源 = " + event.getSource());
        System.out.println("注解方式--接收到MyEvent事件，发送短信/发送邮件");
    }
}