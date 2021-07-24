package com.lordsl.unit.example;

import com.lordsl.unit.common.Info;
import org.springframework.stereotype.Component;

@Component
public class Hello {
    public void hello() {
        Info.YellowText("Hello bean say hello");
    }
}
