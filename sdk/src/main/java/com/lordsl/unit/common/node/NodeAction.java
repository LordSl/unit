package com.lordsl.unit.common.node;

import com.lordsl.unit.common.Dictator;
import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.schema.KeySchema;
import com.lordsl.unit.common.schema.MethodSchema;
import com.lordsl.unit.common.util.Container;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeAction {
    private List<NodeEntry<Field>> consumes, produces, throughs, updates, refers;
    private List<NodeEntry<Field>> inputs, deletes, outputs;
    private List<NodeEntry<Method>> handles;

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

    private static Boolean isReferenceExceptString(Field field) {
        Class<?> cla = field.getType();
        return isReferenceExceptString(cla);
    }

    private static Boolean isReferenceExceptString(Class<?> cla) {
        if (cla.equals(String.class)) return false;
        if (cla.isPrimitive()) return false;
        try {
            if (((Class<?>) (cla.getField("TYPE").get(null))).isPrimitive())
                return false;
        } catch (Exception ignored) {
        }
        return true;
    }

    public NodeAction consumes(List<KeySchema> consumeList) {
        this.consumes = Converter.KeySchemaList2EntryList(consumeList);
        return this;
    }

    public NodeAction produces(List<KeySchema> produceList) {
        this.produces = Converter.KeySchemaList2EntryList(produceList);
        return this;
    }

    public NodeAction throughs(List<KeySchema> throughList) {
        this.throughs = Converter.KeySchemaList2EntryList(throughList);
        return this;
    }

    public NodeAction updates(List<KeySchema> updateList) {
        this.updates = Converter.KeySchemaList2EntryList(updateList);
        return this;
    }

    public NodeAction refers(List<KeySchema> referList) {
        this.refers = Converter.KeySchemaList2EntryList(referList);
        return this;
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

    public NodeAction methods(List<MethodSchema> methodList) {
        this.handles = Converter.MethodSchema2EntryList(methodList);
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
                .filter(item -> !((isReferenceExceptString(item.getVal()) && updates.contains(item))))
                .collect(Collectors.toList());
        return this;
    }

}
