package org.com.lordsl.unit.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Dictator {
    private static final Map<Class<?>, List<Node>> flowNodesMap = new HashMap<>();
    private static final Function<Map<String, Class<?>>, JSONArray> readProperty = (map) -> {
        JSONArray ja = new JSONArray();
        map.entrySet().forEach(
                (entry) -> {
                    JSONObject jo = new JSONObject();
                    jo.put("key", entry.getKey());
                    jo.put("fullClass", entry.getValue().getName());
                    jo.put("simpleClass", entry.getValue().getSimpleName());
                    ja.add(jo);
                }
        );
        return ja;
    };
    private static final Function<Node, JSONObject> readHandlerUnit = (node) -> {
        JSONObject jo = new JSONObject();
        jo.put("order", node.getOrder().toString());
        jo.put("simpleClass", node.getCla().getSimpleName());
        jo.put("fullClass", node.getCla().getName());

        JSONArray produces = new JSONArray();
        JSONArray consumes = new JSONArray();
        JSONArray throughs = new JSONArray();

        try {
            produces = readProperty.apply(node.getProduces());
            consumes = readProperty.apply(node.getConsumes());
            throughs = readProperty.apply(node.getThroughs());
        } catch (Exception ignored) {
            ;
        }
        jo.put("produces", produces);
        jo.put("consumes", consumes);
        jo.put("throughs", throughs);
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

    protected static void put(Class<?> flow, Node newNode) {
        List<Node> nodes;

        if (!flowNodesMap.containsKey(flow)) {
            nodes = new LinkedList<>();
            Node barrier = new Node();
            barrier.setOrder(Float.MAX_VALUE);
            nodes.add(barrier);
            flowNodesMap.put(flow, nodes);
        } else
            nodes = flowNodesMap.get(flow);

        int index = 0;
        for (; index < nodes.size(); index++) {
            Node thisNode = nodes.get(index);
            //不允许order相等
            if (thisNode.getOrder().equals(newNode.getOrder()))
                return;
            if (thisNode.getOrder() > newNode.getOrder())
                break;
        }
        float tmp = index > 0 ? newNode.getOrder() - nodes.get(index - 1).getOrder() : 0;
        if (0 < tmp && tmp <= 0.1)
            nodes.set(index - 1, newNode);
        else
            nodes.add(index, newNode);
    }

    protected static List<Node> get(Class<?> flow) {
        return flowNodesMap.get(flow);
    }

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
                info.add(readHandlerUnit.apply(node));
            }
            fileOutput.apply(jo);
        });
        filePath = filepathCopy;
    }
}
