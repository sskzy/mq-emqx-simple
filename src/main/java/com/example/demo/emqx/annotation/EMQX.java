package com.example.demo.emqx.annotation;

import java.lang.annotation.*;

/**
 * 标记类 用于快速定位主题
 *
 * @author : songtc
 * @since : 2023/12/11 11:27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface EMQX {
    /** 名称标记字段 不包含任何业务逻辑 */
    String name() default "";
}
