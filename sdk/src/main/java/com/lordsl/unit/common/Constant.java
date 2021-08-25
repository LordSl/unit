package com.lordsl.unit.common;

public enum Constant {

    HandlerInitTaskKey("HandlerInitTask"),
    ReferInjectTaskKey("ReferInjectTask"),
    FlowInitTaskKey("FlowInitTask"),
    ;

    private Object val;

    Constant(Object val) {
        this.val = val;
    }

    public <T> T val() {
        return (T) val;
    }

    public String text() {
        return (String) val;
    }
}
