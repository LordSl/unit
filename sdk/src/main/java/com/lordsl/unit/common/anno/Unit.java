package com.lordsl.unit.common.anno;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unit {

    Uni[] unis();

}
