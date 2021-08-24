package com.lordsl.unit.common.node;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.anno.*;
import com.lordsl.unit.common.schema.KeySchema;
import com.lordsl.unit.common.schema.MethodSchema;
import com.lordsl.unit.common.schema.NodeSchema;
import com.lordsl.unit.common.util.Info;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Converter {
    public static NodeSchema Node2NodeSchema(Node node) {
        //todo node被改变的情况下，需要重新读取schema信息
        return node.getNodeSchema();
    }

    public static KeySchema Entry2KeySchema(NodeEntry<Field> entry) {
        KeySchema keySchema = new KeySchema();
        keySchema.setName(entry.getKey());
        keySchema.setFieldName(entry.getVal().getName());
        keySchema.setSimpleClass(entry.getVal().getType().getSimpleName());
        keySchema.setFullClass(entry.getVal().getType().getName());
        keySchema.setNodeClass(entry.getVal().getDeclaringClass().getName());
        return keySchema;
    }

    public static MethodSchema Entry2MethodSchema(NodeEntry<Method> entry) {
        MethodSchema methodSchema = new MethodSchema();
        methodSchema.setMethodName(entry.getVal().getName());
        methodSchema.setPos(entry.getKey());
        methodSchema.setNodeClass(entry.getVal().getDeclaringClass().getName());
        return methodSchema;
    }

    public static List<NodeEntry<Field>> KeySchemaList2EntryList(List<KeySchema> keySchemaList) {
        List<NodeEntry<Field>> nodeEntryList = new ArrayList<>();
        keySchemaList.forEach(
                schema -> {
                    try {
                        Class<?> nodeModelCla = Class.forName(schema.getNodeClass());
                        Field field = nodeModelCla.getDeclaredField(schema.getFieldName());
                        nodeEntryList.add(new NodeEntry<>(schema.getName(), field));
                    } catch (Exception ignored) {
                    }
                }
        );
        if (nodeEntryList.size() != keySchemaList.size()) {
            Info.PurpleAlert("error in convert field from KeySchema");
        }
        return nodeEntryList;
    }

    public static List<NodeEntry<Method>> MethodSchema2EntryList(List<MethodSchema> methodSchemaList) {
        List<NodeEntry<Method>> nodeEntryList = new ArrayList<>();
        methodSchemaList.forEach(
                schema -> {
                    try {
                        Class<?> nodeModelCla = Class.forName(schema.getNodeClass());
                        Method method = nodeModelCla.getDeclaredMethod(schema.getMethodName());
                        nodeEntryList.add(new NodeEntry<>(schema.getPos(), method));
                    } catch (Exception ignored) {
                    }
                }
        );
        if (nodeEntryList.size() != methodSchemaList.size()) {
            Info.PurpleAlert("error in convert method from MethodSchema");
        }
        nodeEntryList.sort(
                (entry1, entry2) -> {
                    Float f1 = Float.parseFloat(entry1.getKey());
                    Float f2 = Float.parseFloat(entry2.getKey());
                    return f1.compareTo(f2);
                }
        );
        return nodeEntryList;
    }

    public static List<KeySchema> Map2KeySchemaList(Map<String, Field> map) {
        List<KeySchema> keySchemaList = new ArrayList<>();
        map.forEach(
                (name, field) -> {
                    KeySchema schema = new KeySchema();
                    schema.setNodeClass(field.getDeclaringClass().getName());
                    schema.setFullClass(field.getType().getName());
                    schema.setFieldName(field.getName());
                    schema.setSimpleClass(field.getType().getSimpleName());
                    schema.setName(name);
                    keySchemaList.add(schema);
                }
        );
        return keySchemaList;
    }

    public static List<MethodSchema> Map2MethodSchemaList(Map<String, Method> map) {
        List<MethodSchema> methodSchemaList = new ArrayList<>();
        map.forEach(
                (pos, method) -> {
                    MethodSchema schema = new MethodSchema();
                    schema.setPos(pos);
                    schema.setMethodName(method.getName());
                    schema.setNodeClass(method.getDeclaringClass().getName());
                    methodSchemaList.add(schema);
                }
        );
        return methodSchemaList;
    }

    public static List<NodeSchema> AnnotatedNodeModel2NodeSchemaList(NodeModel nodeModel) {
        List<NodeSchema> schemaList = new ArrayList<>();
        Class<? extends NodeModel> nodeClass = nodeModel.getClass();

        Unit unit = nodeClass.getAnnotation(Unit.class);
        if (null == unit) {
            Info.BlueInfo(String.format("%s is not a handler node", nodeClass.getName()));
            return schemaList;
        }

        for (Uni uni : unit.unis()) {
            NodeSchema schema = new NodeSchema();
            schema.setProduceList(Map2KeySchemaList(parseField(Produce.class, nodeClass)));
            schema.setConsumeList(Map2KeySchemaList(parseField(Consume.class, nodeClass)));
            schema.setThroughList(Map2KeySchemaList(parseField(Through.class, nodeClass)));
            schema.setUpdateList(Map2KeySchemaList(parseField(Update.class, nodeClass)));
            schema.setReferList(Map2KeySchemaList(parseField(Refer.class, nodeClass)));
            schema.setMethodList(Map2MethodSchemaList(getHandleMethods(nodeClass, uni.flow())));
            schema.setSimpleClass(nodeClass.getSimpleName());
            schema.setFullClass(nodeClass.getName());
            schema.setFlow(uni.flow().getName());
            schema.setOrder(uni.order());
            schemaList.add(schema);
        }

        return schemaList;
    }

    private static Map<String, Method> getHandleMethods(Class<? extends NodeModel> nodeClass, Class<? extends NodeModel> flowClass) {
        Map<Method, Handle> map = AnnoUtil.getMethodAnnoMap(Handle.class, nodeClass);
        Map<String, Method> res = new HashMap<>();
        map.forEach(
                (method, handle) -> {
                    List<Class<? extends NodeModel>> flows = Arrays.asList(handle.flows());
                    if (flows.size() == 0 || flows.contains(flowClass))
                        res.put(handle.pos(), method);
                }
        );
        return res;
    }

    private static <T extends Annotation> Map<String, Field> mapByName(Map<Field, T> sourceMap) {
        Map<String, Field> map = new HashMap<>();
        Optional<T> annoExample = sourceMap.values().stream().findAny();
        if (!annoExample.isPresent()) return map;
        try {
            Method target = annoExample.get().getClass().getDeclaredMethod("name");
            sourceMap.forEach(
                    (field, anno) -> {
                        try {
                            String name = (String) target.invoke(anno);
                            if (null != anno) {
                                map.put(name.equals("") ? field.getName() : name, field);
                            }
                        } catch (Exception ignored) {
                        }
                    }
            );
        } catch (Exception ignored) {
        }
        return map;
    }

    private static <T extends Annotation> Map<String, Field> parseField(Class<T> annoCla, Class<?> objCla) {
        return mapByName(AnnoUtil.getFieldAnnoMap(annoCla, objCla));
    }
}
