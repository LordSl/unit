package com.lordsl.unit.state;

import com.lordsl.unit.compiler.LogicContextModel;
import com.lordsl.unit.util.Container;

public class LogicContextContainer extends Container implements LogicContextModel {

    public LogicContextContainer() {
    }

    @Override
    public Number get(String key) {
        return super.get(key);
    }

    @Override
    public void put(String key, Number val) {
        super.put(key, val);
    }
}
