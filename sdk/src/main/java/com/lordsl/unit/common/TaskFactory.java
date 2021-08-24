package com.lordsl.unit.common;

import com.lordsl.unit.common.node.Converter;
import com.lordsl.unit.common.node.Node;
import com.lordsl.unit.common.schema.NodeSchema;
import com.lordsl.unit.common.util.Info;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class TaskFactory {

    static List<Runnable> getHandlerInitTask(NodeModel nodeModel) {
        List<NodeSchema> nodeSchemaList = Converter.AnnotatedNodeModel2NodeSchemaList(nodeModel);

        return nodeSchemaList.stream()
                .map(nodeSchema -> getHandlerInitTask(nodeModel, nodeSchema))
                .collect(Collectors.toList());
    }

    static List<Runnable> getHandlerInitTask(List<NodeModel> nodeModelList, List<NodeSchema> nodeSchemaList) {
        List<Runnable> tasks = new ArrayList<>();
        nodeSchemaList.forEach(
                nodeSchema -> {
                    try {
                        Class<? extends NodeModel> nodeClass = Class.forName(nodeSchema.getFullClass()).asSubclass(NodeModel.class);
                        nodeModelList.stream().filter(n -> n.getClass().equals(nodeClass)).findAny().ifPresent(nodeModel -> tasks.add(getHandlerInitTask(nodeModel, nodeSchema)));
                    } catch (Exception e) {
                        Info.PurpleAlert("class define in schema not found");
                    }
                }
        );
        return tasks;
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