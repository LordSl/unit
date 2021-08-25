package com.lordsl.unit.common;

public enum Constant {

    HandlerInit("HandlerInitTask"),
    ReferInject("ReferInjectTask"),
    FlowInit("FlowInitTask"),
    ;

    private Object val;

    Constant(Object val) {
        this.val = val;
    }

    public <T> T val() {
        return (T) val;
    }
}
