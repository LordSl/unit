package com.lordsl.unit.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface FlowModel {
    default void lazyInit() {
        if (!Inner.functionsMap.containsKey(this.getClass())) {
            synchronized (this) {
                if (!Inner.functionsMap.containsKey(this.getClass())) {
                    Signal.setOff();//第一个FlowModel的注册必须在所有HandlerModel之后
                    Inner.functionsMap.put(this.getClass(), Adapter.buildSimple(this));
                }
            }
        }
    }

    default Container execAsChain(Container container) {
        lazyInit();
        List<Function<Container, Container>> functions = Inner.functionsMap.get(this.getClass());

        for (Function<Container, Container> function : functions)
            container = function.apply(container);

        return container;
    }

    class Inner {
        volatile static Map<Class<?>, List<Function<Container, Container>>> functionsMap = new HashMap<>();
    }

}
