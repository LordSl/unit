package com.lordsl.unit.common;

import com.lordsl.unit.common.node.Converter;
import com.lordsl.unit.common.node.Node;
import com.lordsl.unit.common.schema.NodeSchema;
import com.lordsl.unit.common.util.Info;

import java.util.List;

class TaskFactory {

    static Runnable getHandlerInitTask(NodeModel nodeModel) {
        return () -> {
            List<NodeSchema> nodeSchemaList = Converter.AnnotatedNodeModel2NodeSchemaList(nodeModel);
            nodeSchemaList.forEach(
                    nodeSchema -> getHandlerInitTask(nodeModel, nodeSchema).run()
            );
        };
    }

    static Runnable getHandlerInitTask(NodeModel model, NodeSchema schema) {
        return () -> new Node()
                .from(model, schema)
                .build()
                .regis();
    }

    static Runnable getFlowInitTask(NodeModel nodeModel) {
        return () -> Dictator.buildConductFunction(nodeModel.getClass());
    }

    static Runnable getFinalDoneTask() {
        Info.BlueInfo("done");
        return () -> {
            TaskResolver.resolveHandlerInitTasks();
            TaskResolver.resolveReferTasks();
            TaskResolver.resolveFlowInitTasks();
        };
    }


}