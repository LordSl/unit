package org.com.lordsl.unit.common;

import java.util.List;

public abstract class AbstractFlow {
    public List<HandlerUnit> units;
    public boolean initialized = false;

    public void init() {
        units = Dictator.get(this.getClass());
    }


    public Container execAsChain(Container container) {
        if (!initialized) {
            init();
            initialized = true;
        }

        //忽略最后一个
        for (int i = 0; i < units.size() - 1; i++)
            container = units.get(i).getFunction().apply(container);
        return container;
    }
}
