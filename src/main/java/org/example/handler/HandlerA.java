package org.example.handler;

import org.springframework.stereotype.Component;

@Unit(order = "1.1")
@Component
public class HandlerA extends AbstractHandler {
    @Output
    String home;
    @Output
    String age;
    @Output
    Long length;

    public HandlerA(Manager manager) {
        super(manager);
    }

    @Override
    void handle() {
        home = "a handle home";
        age = "a handle age";
        length = 0L;
        System.out.println("a handle finish");
    }
}
