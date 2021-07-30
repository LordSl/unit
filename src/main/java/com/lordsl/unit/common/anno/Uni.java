package com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Uni {
    //在flow中的位置
    String order();

    //要绑定的flow的id
    Class<?> flow();

    //自己的id
    String id() default "";

}
