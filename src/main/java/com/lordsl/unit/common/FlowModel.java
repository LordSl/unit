package com.lordsl.unit.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface FlowModel {
    default void check() {
        if (!Inner.initializedMap.containsKey(this.getClass())) {
            synchronized (this) {
                if (!Inner.initializedMap.containsKey(this.getClass())) {
                    Signal.setOff();//第一个FlowModel的注册必须在所有HandlerModel之后
                    Inner.functionsMap.put(this.getClass(), NodeCenter.buildSimple(this));
                    Inner.initializedMap.put(this.getClass(), true);
                }
            }
        }
    }

    default Container execAsChain(Container container) {
        check();
        List<Function<Container, Container>> functions = Inner.functionsMap.get(this.getClass());

        for (Function<Container, Container> function : functions)
            container = function.apply(container);

        return container;
    }

    class Inner {
        volatile static Map<Class<?>, Boolean> initializedMap = new HashMap<>();
        volatile static Map<Class<?>, List<Function<Container, Container>>> functionsMap = new HashMap<>();
    }

}
