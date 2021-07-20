package org.example.alpha;

import org.example.util.AbstractHandler;
import org.example.util.Output;
import org.example.util.Unit;
import org.springframework.stereotype.Component;

@Unit(order = "1.1", flow = FlowAlpha.class)
@Component
public class HandlerA extends AbstractHandler {
    @Output
    String home;
    @Output
    String age;
    @Output
    Long length;

    @Override
    public void handle() {
        home = "a handle home";
        age = "a handle age";
        length = 0L;
        System.out.println("a handle finish");
    }
}
