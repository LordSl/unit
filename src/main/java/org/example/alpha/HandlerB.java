package org.example.alpha;

import org.example.util.AbstractHandler;
import org.example.util.Input;
import org.example.util.Output;
import org.example.util.Unit;
import org.springframework.stereotype.Component;

@Unit(order = "1.7", flow = FlowAlpha.class)
@Component
public class HandlerB extends AbstractHandler {
    @Input(name = "home")
    @Output
    String home;
    @Input(name = "age")
    @Output
    String superUnusualAge;
    @Input
    @Output
    Long length;

    @Override
    public void handle() {
        home = home + "/b handle home";
        superUnusualAge = superUnusualAge + "/b handle age";
        length = length + 101;
        System.out.println("b handle finish");
    }
}
