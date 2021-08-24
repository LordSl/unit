package com.lordsl.unit.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lordsl.unit.common.node.Converter;
import com.lordsl.unit.common.node.Node;
import com.lordsl.unit.common.schema.NodeSchema;
import com.lordsl.unit.common.util.Info;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SchemaResolver {

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
                node -> schemaList.add(Converter.Node2NodeSchema(node))
        );
        fileOutput(path, schemaList);
    }


    static List<NodeSchema> readNodeSchemaList(String path) {
        StringBuilder s = new StringBuilder();
        try {
            String json = new BufferedReader(new FileReader(path))
                    .lines()
                    .collect(Collectors.joining());
            return JSONArray.parseArray(json, NodeSchema.class).stream()
                    .filter(Objects::nonNull)
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
//                    .collect(Collectors.toList());
        } catch (Exception e) {
            Info.PurpleAlert("schema json file read error");
            return null;
        }
    }
}
