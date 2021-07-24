package org.com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Produce {
    String name() default "";
}
