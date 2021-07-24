package com.lordsl.unit.common;

public interface HandlerModel {
    default void init() {
        if (Signal.isOn())
            NodeCenter.regisSimple(this);
    }

    void handle();

}
