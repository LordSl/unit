package org.example.handler;

import org.springframework.stereotype.Component;

@Unit(order = "1.7")
@Component
public class HandlerB extends AbstractHandler {
    @Input(name = "home")
    @Output
    String home;
    @Input(name = "age")
    @Output
    String age;
    @Input
    @Output
    Long length;

    public HandlerB(Manager manager) {
        super(manager);
    }

    @Override
    void handle() {
        home = home + "/b handle home";
        age = age + "/b handle age";
        length = length + 101;
        System.out.println("b handle finish");
    }
}
