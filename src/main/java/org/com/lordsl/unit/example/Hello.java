package org.com.lordsl.unit.example;

import org.com.lordsl.unit.common.Info;
import org.springframework.stereotype.Component;

@Component
public class Hello {
    public void hello() {
        Info.YellowText("Hello bean say hello");
    }
}
