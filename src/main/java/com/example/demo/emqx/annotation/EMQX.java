package com.example.demo.emqx.annotation;

import java.lang.annotation.*;

/**
 * @author : songtc
 * @detail : 标记类 用于快速定位主题
 * @since : 2023/12/11 11:27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface EMQX {
    /**
     *
     */
}
