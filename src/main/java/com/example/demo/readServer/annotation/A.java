package com.example.demo.readServer.annotation;

import java.lang.annotation.*;

/**
 * @author : songtc
 * @since : 2023/12/10 20:48
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface A {
    String value();
}
