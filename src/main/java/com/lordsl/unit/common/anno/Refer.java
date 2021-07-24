package com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Refer {
    String name() default "";
}
