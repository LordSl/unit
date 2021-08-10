package com.lordsl.unit.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Info {
    private static final Map<Class<? extends FlowModel>, List<Node>> flowNodesMap = Dictator.getFlowNodesMap();
    private static final Function<Map<String, Class<?>>, JSONArray> readField = (map) -> {
        JSONArray ja = new JSONArray();
        map.forEach((key, value) -> {
            JSONObject jo = new JSONObject();
            jo.put("key", key);
            jo.put("fullClass", value.getName());
            jo.put("simpleClass", value.getSimpleName());
            ja.add(jo);
        });
        return ja;
    };
    private static final Function<Node, JSONObject> readHandlerModel = (node) -> {
        JSONObject jo = new JSONObject();
        jo.put("order", node.getOrder().toString());
        jo.put("simpleClass", node.getCla().getSimpleName());
        jo.put("fullClass", node.getCla().getName());

        JSONArray produces = new JSONArray();
        JSONArray consumes = new JSONArray();
        JSONArray throughs = new JSONArray();
        JSONArray updates = new JSONArray();
        JSONArray refers = new JSONArray();

        try {
            produces = readField.apply(node.getProduces());
            consumes = readField.apply(node.getConsumes());
            throughs = readField.apply(node.getThroughs());
            updates = readField.apply(node.getUpdates());
            refers = readField.apply(node.getRefers());
        } catch (Exception ignored) {
        }
        jo.put("produces", produces);
        jo.put("consumes", consumes);
        jo.put("throughs", throughs);
        jo.put("updates", updates);
        jo.put("refers", refers);
        return jo;
    };
    private static String filePath = "schema.json";
    private static final Function<JSONObject, Void> fileOutput = (JSONObject jo) -> {
        //输出为json文件
        try {
            String prefix = filePath;
            if (filePath.endsWith(".json"))
                prefix = filePath.substring(0, filePath.length() - 5);
            File file = new File(String.format("%s-%s.json", prefix, jo.get("simpleClass")));
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
            osw.write(jo.toJSONString());
            osw.close();
        } catch (Exception ignored) {
        }
        return null;
    };

    public static void outToJson() {
        outToJson(filePath);
    }

    public static void outToJson(String path) {
        String filepathCopy = filePath;
        filePath = path;
        flowNodesMap.forEach((flow, units) -> {
            JSONObject jo = new JSONObject();
            jo.put("simpleClass", flow.getSimpleName());
            jo.put("fullClass", flow.getName());
            JSONArray info = new JSONArray();
            jo.put("info", info);

            for (int i = 0; i < units.size() - 1; i++) {
                Node node = units.get(i);
                info.add(readHandlerModel.apply(node));
            }
            fileOutput.apply(jo);
        });
        filePath = filepathCopy;
    }

    private static void printWithColor(String s, int code) {
        System.out.printf("\u001B[%dm%s\u001B[0m%n", code, s);
    }

    public static void YellowText(String s) {
        printWithColor(String.format("[TEXT] %s", s), 33);
    }

    public static void BlueInfo(String s) {
        printWithColor(String.format("[INFO] %s", s), 34);
    }

    public static void PurpleAlert(String s) {
        printWithColor(String.format("[ALERT] %s", s), 35);
    }

    public static void GreenLog(String s) {
        printWithColor(String.format("[LOG] %s", s), 36);
    }


}
