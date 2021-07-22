package org.com.lordsl.unit.alpha;

import org.com.lordsl.unit.common.AbstractHandler;
import org.com.lordsl.unit.common.Unit;
import org.com.lordsl.unit.common.Through;
import org.springframework.stereotype.Component;

@Unit(order = "1.7", flow = FlowAlpha.class)
@Component
public class HandlerB extends AbstractHandler {
    @Through(name = "home")
    String home;
    @Through(name = "age")
    String superUnusualAge;
    @Through
    Long length;

    @Override
    public void handle() {
        home = home + "/b handle home";
        superUnusualAge = superUnusualAge + "/b handle age";
        length = length + 101;
        System.out.println("b handle finish");
    }
}
