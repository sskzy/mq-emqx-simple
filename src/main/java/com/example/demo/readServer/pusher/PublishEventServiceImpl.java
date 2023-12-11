package com.example.demo.readServer.pusher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PublishEventServiceImpl {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Async   // 异步注解
    public void publishEvent() {
        System.out.println("业务代码已完成，下面开始发送短信及邮件");
        // 发送一个事件可以让发送短信和邮件都接收同一个事件（如果需要传不同的参数，就需要发布两个不同的事件）
        applicationEventPublisher.publishEvent(new MyEvent("事件源--自定义的事件触发了。。。"));
        applicationEventPublisher.publishEvent(new MyEvent2("事件源--自定义的事件触发了。。。"));
    }
}