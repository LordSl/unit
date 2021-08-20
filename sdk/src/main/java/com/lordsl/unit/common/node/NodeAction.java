package com.lordsl.unit.common.node;

import com.lordsl.unit.common.Dictator;
import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.ParseUtil;
import com.lordsl.unit.common.util.Container;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeAction {
    private List<NodeEntry<Field>> consumes, produces, throughs, updates, refers;
    private List<NodeEntry<Field>> inputs, deletes, outputs;
    private List<NodeEntry<Method>> handles;
    private Supplier<NodeModel> templateSupplier;

    public List<NodeEntry<Field>> refers() {
        return refers;
    }

    public List<NodeEntry<Field>> consumes() {
        return consumes;
    }

    public List<NodeEntry<Field>> produces() {
        return produces;
    }

    public List<NodeEntry<Field>> throughs() {
        return throughs;
    }

    public List<NodeEntry<Field>> updates() {
        return updates;
    }

    public List<NodeEntry<Method>> handles() {
        return handles;
    }

    public NodeAction consumes(Map<String, Field> consumes) {
        this.consumes = NodeEntry.convertFromMap(consumes);
        return this;
    }

    public NodeAction produces(Map<String, Field> produces) {
        this.produces = NodeEntry.convertFromMap(produces);
        return this;
    }

    public NodeAction throughs(Map<String, Field> throughs) {
        this.throughs = NodeEntry.convertFromMap(throughs);
        return this;
    }

    public NodeAction updates(Map<String, Field> updates) {
        this.updates = NodeEntry.convertFromMap(updates);
        return this;
    }

    public NodeAction refers(Map<String, Field> refers) {
        this.refers = NodeEntry.convertFromMap(refers);
        return this;
    }

    public NodeAction methods(Map<String, Method> methods) {
        this.handles = NodeEntry.convertFromSortKeyMap(methods);
        return this;
    }

    public NodeAction templateSupplier(Supplier<NodeModel> templateSupplier) {
        this.templateSupplier = templateSupplier;
        return this;
    }

    public NodeAction build() {
        //consumes + throughs + updates
        inputs = Stream.of(
                consumes, throughs, updates)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        //consumes - throughs - updates
        deletes = consumes.stream()
                .filter(item -> !throughs.contains(item) && !updates.contains(item))
                .collect(Collectors.toList());

        //produces + updates
        outputs = Stream.of(
                produces, updates)
                .flatMap(Collection::stream)
                .filter(item -> !((ParseUtil.isReferenceExceptString(item.getVal()) && updates.contains(item))))
                .collect(Collectors.toList());
        return this;
    }


    NodeModel doInit() {
        return templateSupplier.get();
    }

    void doInput(NodeModel nodeModel, Container container) throws Exception {
        //bean注入
        for (NodeEntry<Field> entry : refers) {
            Field f = entry.getVal();
            f.set(nodeModel, Dictator.getRefer(entry.getKey()));
        }
        //从容器输入
        for (NodeEntry<Field> entry : inputs) {
            Field f = entry.getVal();
            f.set(nodeModel, container.get(entry.getKey()));
        }
    }

    void doDelete(Container container) {
        //从容器删除
        for (NodeEntry<Field> entry : deletes) {
            container.remove(entry.getKey());
        }
    }

    void doHandles(NodeModel nodeModel) throws Exception {
        for (NodeEntry<Method> entry : handles) {
            entry.getVal().invoke(nodeModel);
        }
    }

    void doOutput(NodeModel nodeModel, Container container) throws Exception {
        //向容器输出
        for (NodeEntry<Field> entry : outputs) {
            Field f = entry.getVal();
            container.put(entry.getKey(), f.get(nodeModel));
        }
    }

}
