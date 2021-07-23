package org.com.lordsl.unit.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractFlow {
    protected List<HandlerUnit> units;
    protected boolean initialized = false;
    protected Map<String, Class<?>> params = new HashMap<>();

    private void init() {
        units = Dictator.get(this.getClass());
    }

    protected <T> void setParam(Container container, T t, Class<?> cla, String name) {
        container.put(name, t);
        if (!initialized)
            params.put(name, cla);
    }

    Map<String, Class<?>> getParams() {
        setParams(new Container());
        return params;
    }

    protected abstract void setParams(Container container);

    public Container execAsChain(Container container) {
        if (!initialized) {
            init();
            initialized = true;
        }

        for (int i = 0; i < units.size() - 1; i++)
            container = units.get(i).getFunction().apply(container);
        return container;
    }
}
