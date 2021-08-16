package com.lordsl.unit.common;

public interface FlowModel {

    class Stand {
        public static void init(FlowModel model) {
            TaskResolver.addFlowInitTask(Adapter.getFlowInitTask(Mode.simple, model));
        }

        private static void check() {
            if (Signal.regisEnable()) {
                synchronized (Signal.class) {
                    if (Signal.regisEnable()) {
                        Adapter.getFinalDoneTask(Mode.simple).run();
                        Signal.regisEnable(false);
                    }
                }
            }
        }

        public static Container execAsChain(Container container, FlowModel model) {
            check();
            return Dictator.getFinalFunction(model).apply(container);
        }
    }

}
