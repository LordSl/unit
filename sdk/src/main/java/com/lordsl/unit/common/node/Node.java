package com.lordsl.unit.common.node;

import com.lordsl.unit.common.Dictator;
import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.TaskPool;
import com.lordsl.unit.common.schema.NodeSchema;
import com.lordsl.unit.common.util.Container;
import com.lordsl.unit.common.util.Info;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class Node {
    private NodeAction nodeAction;

    private NodeSchema nodeSchema;

    private NodeModel nodeModel;
    private Class<? extends NodeModel> flow;
    private Float order;

    public NodeSchema getNodeSchema() {
        return nodeSchema;
    }

    public Float getOrder() {
        return order;
    }

    public void setOrder(Float order) {
        this.order = order;
    }

    public NodeAction getNodeAction() {
        return nodeAction;
    }

    public Node from(NodeModel nodeModel, NodeSchema nodeSchema) {
        this.nodeModel = nodeModel;
        this.nodeSchema = nodeSchema;
        return this;
    }

    public Node build() {
        nodeAction = new NodeAction()
                .produces(nodeSchema.getProduceList())
                .consumes(nodeSchema.getConsumeList())
                .refers(nodeSchema.getReferList())
                .throughs(nodeSchema.getThroughList())
                .updates(nodeSchema.getUpdateList())
                .methods(nodeSchema.getMethodList())
                .build();
        order = Float.parseFloat(nodeSchema.getOrder());
        try {
            flow = Class.forName(nodeSchema.getFlow()).asSubclass(NodeModel.class);
        } catch (Exception e) {
            Info.PurpleAlert("class declare in schema not exist or not a impl of NodeModel");
        }
        checkConflicts();
        setAllAccessible();
        return this;
    }

    public void regis() {
        resolveRefers();
        Dictator.regisNode(this);
    }

    private void resolveRefers() {
        TaskPool.addReferTask(() ->
                nodeAction.refers().forEach((entry) -> {
                    try {
                        entry.getVal().setAccessible(true);
                        Dictator.putRefer(entry.getKey(), entry.getVal().get(nodeModel));
                    } catch (Exception e) {
                        Info.PurpleAlert("refer inject exception");
                    }
                }));
    }

    private void setAllAccessible() {
        Stream.of(nodeAction.consumes(), nodeAction.produces(), nodeAction.throughs(), nodeAction.updates(), nodeAction.refers())
                .flatMap(Collection::stream)
                .forEach(
                        entry -> entry.getVal().setAccessible(true)
                );
        nodeAction.handles()
                .forEach(
                        method -> method.getVal().setAccessible(true)
                );
    }

    private void checkConflicts() {
        Map<String, Integer> tmp = new HashMap<>();
        Stream.of(nodeAction.consumes(), nodeAction.produces(), nodeAction.throughs(), nodeAction.updates(), nodeAction.refers())
                .flatMap(Collection::stream)
                .forEach(
                        entry -> tmp.merge(entry.getKey(), 1, (o, n) -> o + 1)
                );
        tmp.forEach(
                (key, value) -> {
                    if (value > 1)
                        Info.PurpleAlert(String.format("key %s has more than 1 specific annotation which may cause error", key));
                }
        );
    }

    public Class<? extends NodeModel> getFlow() {
        return flow;
    }

    public void setFlow(Class<? extends NodeModel> flow) {
        this.flow = flow;
    }

    public Function<Container, Container> getConductFunction() {
        return container -> {
            try {
                NodeModel nodeInstance = nodeModel.getTemplate().get();
                nodeAction.doInput(nodeInstance, container);
                nodeAction.doDelete(container);
                nodeAction.doHandles(nodeInstance);
                nodeAction.doOutput(nodeInstance, container);
                return container;
            } catch (Exception e) {
                Info.PurpleAlert("conduct function runtime exception");
                return null;
            }
        };
    }

    public NodeModel getNodeModel() {
        return nodeModel;
    }


}
