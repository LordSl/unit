package org.example.handler;

import org.springframework.stereotype.Component;

@Unit(order = "1.71")
@Component
public class HandlerC extends AbstractHandler {

    public HandlerC(Manager manager) {
        super(manager);
    }

    @Override
    void handle() {
        System.out.println("c handle finish");
    }
}
