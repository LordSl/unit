package com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 从flow中消费一个字段
 * 该HandlerModel执行完毕后，从flow中删除它，执行顺序在它之后的HandlerModel中无法获取
 * 不使用该注解，字段将永远在flow中流传
 */
public @interface Consume {
    String name() default "";
}
