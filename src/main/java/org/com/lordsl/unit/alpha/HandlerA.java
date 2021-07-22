package org.com.lordsl.unit.alpha;

import org.com.lordsl.unit.common.AbstractHandler;
import org.com.lordsl.unit.common.Produce;
import org.com.lordsl.unit.common.Unit;
import org.springframework.stereotype.Component;

@Unit(order = "1.1", flow = FlowAlpha.class)
@Component
public class HandlerA extends AbstractHandler {
    @Produce
    String home;
    @Produce
    String age;
    @Produce
    Long length;

    @Override
    public void handle() {
        home = "a handle home";
        age = "a handle age";
        length = 0L;
        System.out.println("a handle finish");
    }
}
