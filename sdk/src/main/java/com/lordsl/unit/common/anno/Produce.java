package com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 向flow中生产一个字段
 */
public @interface Produce {
    String name() default "";
}
