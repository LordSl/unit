package org.example.util;

import java.util.function.Function;

public class HandlerUnit {
    private final Float order;
    private final Function<Container, Container> function;
    private final Class<?> cla;//标识来源哪个类

    public HandlerUnit(Class<?> cla, Float order, Function<Container, Container> function) {
        this.cla = cla;
        this.order = order;
        this.function = function;
    }

    public Float getOrder() {
        return order;
    }

    public Function<Container, Container> getFunction() {
        return function;
    }

    public Class<?> getCla() {
        return cla;
    }
}
