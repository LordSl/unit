package com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 从spring中引用一个bean的实例
 * 带有该注解的字段，会产生一个注册任务，在初次运行时，从spring单例中获取该实例，置入一个专用容器
 * 之后每次运行时，都会从专用容器中获取该bean的引用
 */
public @interface Refer {
    String name() default "";
}
