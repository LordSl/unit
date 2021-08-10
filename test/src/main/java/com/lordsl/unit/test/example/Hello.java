package com.lordsl.unit.test.example;

import com.lordsl.unit.common.OpFacade;
import org.springframework.stereotype.Component;

@Component
public class Hello {
    public void hello() {
        OpFacade.YellowText("Hello bean say hello");
    }
}
