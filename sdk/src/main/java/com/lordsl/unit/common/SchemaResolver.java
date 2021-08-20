package com.lordsl.unit.common;

import com.alibaba.fastjson.JSON;
import com.lordsl.unit.common.node.Node;
import com.lordsl.unit.common.node.NodeAction;
import com.lordsl.unit.common.node.NodeEntry;
import com.lordsl.unit.common.schema.KeySchema;
import com.lordsl.unit.common.schema.MethodSchema;
import com.lordsl.unit.common.schema.NodeSchema;
import com.lordsl.unit.common.util.Info;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SchemaResolver {

    private static List<KeySchema> entry2KeySchema(List<NodeEntry<Field>> entryList) {
        List<KeySchema> list = new ArrayList<>();
        entryList.forEach(
                entry -> list.add(NodeEntry.convert2KeySchema(entry))
        );
        return list;
    }

    private static List<MethodSchema> entry2MethodSchema(List<NodeEntry<Method>> entryList) {
        List<MethodSchema> list = new ArrayList<>();
        entryList.forEach(
                entry -> list.add(NodeEntry.convert2MethodSchema(entry))
        );
        return list;
    }

    private static NodeSchema node2NodeSchema(Node node) {
        NodeSchema nodeSchema = new NodeSchema();
        nodeSchema.setSimpleClass(node.getNodeModel().getClass().getSimpleName());
        nodeSchema.setFullClass(node.getNodeModel().getClass().getName());
        nodeSchema.setFlow(node.getFlow().getName());
        nodeSchema.setOrder(node.getOrder().toString());
        NodeAction nodeAction = node.getNodeAction();
        nodeSchema.setProduceList(entry2KeySchema(nodeAction.produces()));
        nodeSchema.setConsumeList(entry2KeySchema(nodeAction.consumes()));
        nodeSchema.setThroughList(entry2KeySchema(nodeAction.throughs()));
        nodeSchema.setUpdateList(entry2KeySchema(nodeAction.updates()));
        nodeSchema.setReferList(entry2KeySchema(nodeAction.refers()));
        nodeSchema.setMethodList(entry2MethodSchema(nodeAction.handles()));
        return nodeSchema;
    }

    private static void fileOutput(String path, Object javaObject) {
        //输出为json文件
        try {
            if (!path.endsWith(".json"))
                path += ".json";
            File file = new File(path);
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
            osw.write(JSON.toJSON(javaObject).toString());
            osw.close();
        } catch (Exception ignored) {
            Info.PurpleAlert("error in file output");
        }
    }

    static void outToJson(String path) {
        outToJson(path, Dictator.getAllNodes());
    }

    static void outToJson(String path, List<Node> nodes) {
        List<NodeSchema> schemaList = new ArrayList<>();
        nodes.forEach(
                node -> schemaList.add(node2NodeSchema(node))
        );
        fileOutput(path, schemaList);
    }
}
