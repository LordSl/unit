package com.lordsl.unit.common.node;

import com.lordsl.unit.common.Dictator;
import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.ParseUtil;
import com.lordsl.unit.common.TaskResolver;
import com.lordsl.unit.common.anno.*;
import com.lordsl.unit.common.schema.NodeSchema;
import com.lordsl.unit.common.util.Container;
import com.lordsl.unit.common.util.Info;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Node {
    private NodeAction nodeAction;
    private NodeModel nodeModel;
    private Class<? extends NodeModel> flow;
    private Float order;

    public Float getOrder() {
        return order;
    }

    public NodeAction getNodeAction() {
        return nodeAction;
    }

    /**
     * 从class注解读取信息
     */
    public Node from(NodeModel nodeModel) {
        nodeAction = new NodeAction()
                .produces(ParseUtil.parseField(Produce.class, nodeModel.getClass()))
                .consumes(ParseUtil.parseField(Consume.class, nodeModel.getClass()))
                .refers(ParseUtil.parseField(Refer.class, nodeModel.getClass()))
                .throughs(ParseUtil.parseField(Through.class, nodeModel.getClass()))
                .updates(ParseUtil.parseField(Update.class, nodeModel.getClass()))
                .templateSupplier(nodeModel.getTemplate());
        this.nodeModel = nodeModel;
        return this;
    }

    public Node to(Class<? extends NodeModel> flow) {
        this.flow = flow;
        nodeAction = nodeAction
                .methods(getHandleMethods());
        return this;
    }

    public Node order(Float order) {
        this.order = order;
        return this;
    }

    private Map<String, Method> getHandleMethods() {
        Map<Method, Handle> map = ParseUtil.getMethodAnnoMap(Handle.class, nodeModel.getClass());
        Map<String, Method> res = new HashMap<>();
        map.forEach(
                (method, handle) -> {
                    List<Class<? extends NodeModel>> flows = Arrays.asList(handle.flows());
                    if (flows.size() == 0 || flows.contains(flow))
                        res.put(handle.pos(), method);
                }
        );
        return res;
    }

    public NodeModel getNodeModel() {
        return nodeModel;
    }

    /**
     * 任意方式得到的schema，作用与注解相同，优先级高于注解
     */
    public Node reshapeBy(NodeSchema nodeSchema) {
        //todo
        return this;
    }

    public void build() {
        checkConflicts();
        setAllAccessible();
        resolveRefers();
        nodeAction.build();
        Dictator.regisNode(this);
    }

    private void resolveRefers() {
        TaskResolver.addReferTask(() ->
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

    public Function<Container, Container> getConductFunction() {
        return container -> {
            try {
                NodeModel nodeModel = nodeAction.doInit();
                nodeAction.doInput(nodeModel, container);
                nodeAction.doDelete(container);
                nodeAction.doHandles(nodeModel);
                nodeAction.doOutput(nodeModel, container);
                return container;
            } catch (Exception e) {
                Info.PurpleAlert("conduct function runtime exception");
                return null;
            }
        };
    }


}
