package com.lordsl.unit.common;

import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import com.lordsl.unit.common.node.Node;
import com.lordsl.unit.common.schema.NodeSchema;

class TaskFactory {

    static Runnable getHandlerInitTask(NodeModel nodeModel) {
        return () -> {
            Unit unit = nodeModel.getClass().getAnnotation(Unit.class);
            if (null == unit)
                return;
            for (Uni uni : unit.unis()) {
                new Node()
                        .from(nodeModel)
                        .to(uni.flow())
                        .order(Float.parseFloat(uni.order()))
                        .build();
            }
        };
    }

    static Runnable getHandlerInitTask(NodeModel model, NodeSchema schema) {
        return () -> new Node()
                .from(model)
                .reshapeBy(schema)//schema中指明了to和order
                .build();
    }

    static Runnable getFlowInitTask(NodeModel nodeModel) {
        return () -> Dictator.polyToConductFunction(nodeModel.getClass());
    }

    static Runnable getFinalDoneTask() {
        return () -> {
            TaskResolver.resolveHandlerInitTasks();
            TaskResolver.resolveReferTasks();
            TaskResolver.resolveFlowInitTasks();
        };
    }


}