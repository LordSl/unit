package com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Handle {
    String pos() default "1.0";

    Class<?>[] flows() default {};
}
