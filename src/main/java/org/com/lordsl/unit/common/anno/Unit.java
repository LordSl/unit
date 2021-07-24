package org.com.lordsl.unit.common.anno;

import org.com.lordsl.unit.common.FlowModel;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unit {
    //在flow中的位置
    String[] order() default "0.0";

    //要绑定的flow的id
    Class<?>[] flow() default FlowModel.class;

    //自己的id
    String id() default "";


}
