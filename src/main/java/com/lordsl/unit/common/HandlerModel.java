package com.lordsl.unit.common;

public interface HandlerModel {
    default void init() {
        if (Signal.isOn())
            Adapter.regisSimple(this);
    }

    default HandlerModel getTemplate() {
        return null;
    }

    void handle();

}
