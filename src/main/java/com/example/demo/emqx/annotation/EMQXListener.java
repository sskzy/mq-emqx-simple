package com.example.demo.emqx.annotation;

import java.lang.annotation.*;

/**
 * @author : songtc
 * @since : 2023/12/09 15:48
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EMQXListener {
    String topic();
}
