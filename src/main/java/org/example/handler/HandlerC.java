package org.example.handler;

import org.springframework.stereotype.Component;

@Unit(order = "1.71") //覆盖了HandlerB
@Component
public class HandlerC extends AbstractHandler {
    @Input
    @Output
    String home;

    public HandlerC(Manager manager) {
        super(manager);
    }

    @Override
    void handle() {
        home = home + "/c handle home";
        System.out.println("c handle finish");
    }
}
