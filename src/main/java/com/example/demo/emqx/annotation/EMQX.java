package com.example.demo.emqx.annotation;

import java.lang.annotation.*;

/**
 * @author : songtc
 * @since : 2023/12/11 10:36
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EMQX {
    String topic() default "";
}
