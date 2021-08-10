package com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 从flow中注入一个字段用于写
 * HandlerModel中的处理方法执行完毕后，重新注回flow
 * 当字段是一个引用类型时，不会显式地注回
 */
public @interface Update {
    String name() default "";
}
