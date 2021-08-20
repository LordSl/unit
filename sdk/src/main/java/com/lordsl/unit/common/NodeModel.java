package com.lordsl.unit.common;

import com.lordsl.unit.common.util.Container;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

public interface NodeModel {

    default Supplier<NodeModel> getTemplate() {
        try {
            Constructor<? extends NodeModel> constructor = this.getClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            return () -> {
                try {
                    return constructor.newInstance();
                } catch (Exception ignored) {
                    return null;
                }
            };
        } catch (Exception ignored) {
            return null;
        }
    }

    class Stand {
        public static void initAsFlow(NodeModel nodeModel) {
            TaskResolver.addFlowInitTask(TaskFactory.getFlowInitTask(nodeModel));
        }

        public static void initAsHandler(NodeModel nodeModel) {
            if (Signal.regisEnable())
                TaskResolver.addHandlerInitTask(TaskFactory.getHandlerInitTask(nodeModel));
        }

        private static void check() {
            if (Signal.regisEnable()) {
                synchronized (Signal.class) {
                    if (Signal.regisEnable()) {
                        TaskFactory.getFinalDoneTask().run();
                        Signal.regisEnable(false);
                    }
                }
            }
        }

        public static Container execAsFlow(Container container, NodeModel model) {
            check();
            return Dictator.getConductFunction(model).apply(container);
        }
    }

}
