package org.com.lordsl.unit.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface FlowModel {
    Map<Class<?>, Boolean> initializedMap = new HashMap<>();
    Map<Class<?>, List<Function<Container, Container>>> functionsMap = new HashMap<>();

    default void init() {
        Signal.setOff();//第一个FlowModel的注册必须在所有HandlerModel之后
        initializedMap.put(this.getClass(), true);
        functionsMap.put(this.getClass(), NodeCenter.buildSimple(this));
    }

    default Container execAsChain(Container container) {
        Class<?> cla = this.getClass();
        if (!initializedMap.containsKey(cla)) {
            init();
        }
        List<Function<Container, Container>> functions = functionsMap.get(cla);
        for (Function<Container, Container> function : functions)
            container = function.apply(container);
        return container;
    }

}
