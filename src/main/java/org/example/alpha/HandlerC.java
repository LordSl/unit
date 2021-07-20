package org.example.alpha;

import org.example.util.AbstractHandler;
import org.example.util.Input;
import org.example.util.Output;
import org.example.util.Unit;
import org.springframework.stereotype.Component;

@Unit(order = "1.71", flow = FlowAlpha.class) //覆盖了HandlerB
@Component
public class HandlerC extends AbstractHandler {
    @Input
    @Output
    String home;

    @Override
    public void handle() {
        home = home + "/c handle home";
        System.out.println("c handle finish");
    }
}
