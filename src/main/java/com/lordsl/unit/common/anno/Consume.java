package com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Consume {
    String name() default "";
}
