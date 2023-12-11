package com.example.demo.readServer.pusher;

import org.springframework.context.ApplicationEvent;

/**
 * @author : songtc
 * @since : 2023/12/10 20:30
 */
public class MyEvent2 extends ApplicationEvent {
    public MyEvent2(Object source) {
        super(source);
        System.out.println("Event2");
    }
}
