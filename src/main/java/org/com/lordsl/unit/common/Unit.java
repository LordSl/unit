package org.com.lordsl.unit.common;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unit {
    String[] order();

    Class<?>[] flow();
}
