package com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 从flow中注入一个字段用于读
 * 应避免任何对Through字段的写操作，尤其是引用的改变
 */
public @interface Through {
    String name() default "";
}
