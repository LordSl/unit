package com.lordsl.unit.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface FlowModel {

    class Stand {
        private static final Map<Class<?>, List<Function<Container, Container>>> functionsMap = new HashMap<>();

        static void lazyInit(FlowModel model) {
            if (!functionsMap.containsKey(model.getClass())) {
                synchronized (Stand.class) {
                    if (!functionsMap.containsKey(model.getClass())) {
                        functionsMap.put(model.getClass(), Adapter.buildSimple(model));
                        Signal.setOff();//第一个FlowModel的注册必须在所有HandlerModel之后
                    }
                }
            }
        }

        public static Container execAsChain(Container container, FlowModel model) {
            lazyInit(model);
            List<Function<Container, Container>> functions = functionsMap.get(model.getClass());

            for (Function<Container, Container> function : functions)
                container = function.apply(container);

            return container;
        }
    }

}
