package com.example.demo.emqx.annotation;

import java.lang.annotation.*;

/**
 * @author : songtc
 * @detail : 消息监听器 接收执行器的消息
 * @since : 2023/12/09 15:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface EMQXListener {
    /**
     *
     */
    String topic();
}
