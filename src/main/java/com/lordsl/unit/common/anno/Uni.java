package com.lordsl.unit.common.anno;

import com.lordsl.unit.common.FlowModel;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Uni {
    //在flow中的位置
    String order();

    Class<? extends FlowModel> flow();

}
