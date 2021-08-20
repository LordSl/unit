package com.lordsl.unit.common.anno;

import com.lordsl.unit.common.NodeModel;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Handle {
    String pos() default "1.0";

    Class<? extends NodeModel>[] flows() default {};
}
