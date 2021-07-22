package org.com.lordsl.unit.alpha;

import org.com.lordsl.unit.common.AbstractHandler;
import org.com.lordsl.unit.common.Consume;
import org.com.lordsl.unit.common.Unit;
import org.com.lordsl.unit.common.Through;
import org.springframework.stereotype.Component;

@Unit(order = "1.71", flow = FlowAlpha.class) //覆盖了HandlerB
@Component
public class HandlerC extends AbstractHandler {
    @Through
    String home;
    @Consume
    Long length;

    @Override
    public void handle() {
        home = home + "/c handle home";
        System.out.println("c handle finish");
    }
}
