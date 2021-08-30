package com.lordsl.unit.test.example;

import com.lordsl.unit.util.Info;
import org.springframework.stereotype.Component;

@Component
public class Hello {
    public void hello() {
        Info.YellowText("Hello bean say hello");
    }
}
