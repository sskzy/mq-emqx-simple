package com.example.demo.emqx.annotation;

import java.lang.annotation.*;

/**
 * 消息监听器 接收执行器的消息
 *
 * @author : songtc
 * @since : 2023/12/09 15:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface EMQXListener {
    /** 名称标记字段 不包含任何业务逻辑 */
    String name() default "";

    /** 发布订阅主题 */
    String topic();
}
