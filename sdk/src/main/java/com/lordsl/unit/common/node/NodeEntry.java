package com.lordsl.unit.common.node;

import com.lordsl.unit.common.schema.KeySchema;
import com.lordsl.unit.common.schema.MethodSchema;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodeEntry<T> {
    private String key;
    private T val;

    NodeEntry(String key, T val) {
        this.key = key;
        this.val = val;
    }

    static <T> List<NodeEntry<T>> convertFromMap(Map<String, T> map) {
        return map.entrySet().stream()
                .map(item -> new NodeEntry<>(item.getKey(), item.getValue()))
                .collect(Collectors.toList());
    }

    static <T> List<NodeEntry<T>> convertFromSortKeyMap(Map<String, T> map) {
        List<NodeEntry<T>> entryList = convertFromMap(map);
        entryList.sort(
                (entry1, entry2) -> {
                    Float f1 = Float.parseFloat(entry1.getKey());
                    Float f2 = Float.parseFloat(entry2.getKey());
                    return f1.compareTo(f2);
                }
        );
        return entryList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public static KeySchema convert2KeySchema(NodeEntry<Field> entry) {
        KeySchema keySchema = new KeySchema();
        keySchema.setName(entry.getKey());
        keySchema.setSimpleClass(entry.getVal().getType().getSimpleName());
        keySchema.setFullClass(entry.getVal().getType().getName());
        return keySchema;
    }

    public static MethodSchema convert2MethodSchema(NodeEntry<Method> entry) {
        MethodSchema methodSchema = new MethodSchema();
        methodSchema.setName(entry.getVal().getName());
        methodSchema.setPos(entry.getKey());
        return methodSchema;
    }

}
