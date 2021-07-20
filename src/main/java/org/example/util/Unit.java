package org.example.util;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unit {
    String[] order();

    Class<?>[] flow();
}
