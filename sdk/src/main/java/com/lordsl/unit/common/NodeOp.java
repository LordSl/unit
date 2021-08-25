package com.lordsl.unit.common;

import com.lordsl.unit.common.util.Container;
import com.lordsl.unit.common.util.Info;

class NodeOp {

    static void initAsFlow(NodeModel nodeModel) {
        DefaultTaskPool.put(Const.FlowInitTaskKey, TaskFactory.getFlowInitTask(nodeModel));
    }

    static void initAsHandler(NodeModel nodeModel) {
        if (Signal.regisEnable())
            TaskFactory.getHandlerInitTask(nodeModel).forEach(task -> DefaultTaskPool.put(Const.HandlerInitTaskKey, task));

    }

    private static void check() {
        if (Signal.regisEnable()) {
            synchronized (Signal.class) {
                if (Signal.regisEnable()) {
                    Info.GreenLog("usage detect, start actual load");
                    TaskFactory.getFinalDoneTask().run();
                    Signal.regisEnable(false);
                }
            }
        }
    }

    static Container execAsFlow(Container container, NodeModel model) {
        check();
        return Dictator.getFlowConductFunction(model.getClass()).apply(container);
    }

    static Container execAsHandler(Container container, NodeModel model) {
        return Dictator.getHandlerConductFunction(model.getClass()).apply(container);
    }

    static Container execAsFlow(Container container, Class<? extends NodeModel> modelCla) {
        check();
        return Dictator.getFlowConductFunction(modelCla).apply(container);
    }

    static Container execAsHandler(Container container, Class<? extends NodeModel> modelCla) {
        return Dictator.getHandlerConductFunction(modelCla).apply(container);
    }

}
