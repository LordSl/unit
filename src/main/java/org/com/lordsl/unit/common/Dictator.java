package org.com.lordsl.unit.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Dictator {
    private static final Map<Class<?>, List<HandlerUnit>> flowUnitsMap = new HashMap<>();
    private static final Function<Map<String, Field>, JSONArray> readProperty = (map) -> {
        JSONArray ja = new JSONArray();
        map.entrySet().stream().forEach(
                (entry) -> {
                    JSONObject jo = new JSONObject();
                    jo.put("key", entry.getKey());
                    jo.put("fullClass", entry.getValue().getType().getName());
                    jo.put("simpleClass", entry.getValue().getType().getSimpleName());
                    ja.add(jo);
                }
        );
        return ja;
    };
    private static final Function<HandlerUnit, JSONObject> readHandlerUnit = (handlerUnit) -> {
        JSONObject jo = new JSONObject();
        jo.put("order", handlerUnit.getOrder().toString());
        jo.put("simpleClass", handlerUnit.getCla().getSimpleName());
        jo.put("fullClass", handlerUnit.getCla().getName());

        JSONArray produces = new JSONArray();
        JSONArray consumes = new JSONArray();
        JSONArray throughs = new JSONArray();

        try {
            Signal.setPrepare();
            AbstractHandler instance = (AbstractHandler) handlerUnit.getCla().newInstance();
            produces = readProperty.apply(instance.getProduces());
            consumes = readProperty.apply(instance.getConsumes());
            throughs = readProperty.apply(instance.getThroughs());
            Signal.setRuntime();
        } catch (Exception ignored) {
            ;
        }
        jo.put("produces", produces);
        jo.put("consumes", consumes);
        jo.put("throughs", throughs);
        return jo;
    };
    private static final Function<Class<?>, JSONObject> readDefault = (cla) -> {
        JSONObject jo1 = new JSONObject();
        try {
            AbstractFlow flow = (AbstractFlow) cla.newInstance();
            Map<String, Class<?>> map = flow.getParams();
            JSONArray produces = new JSONArray();
            for (String name : map.keySet()) {
                JSONObject jo2 = new JSONObject();
                jo2.put("key", name);
                jo2.put("simpleClass", map.get(name).getSimpleName());
                jo2.put("fullClass", map.get(name).getName());
                produces.add(jo2);
            }
            jo1.put("order", String.valueOf(Float.MIN_VALUE));
            jo1.put("simpleClass", cla.getSimpleName());
            jo1.put("fullClass", cla.getName());
            jo1.put("produces", produces);
            jo1.put("consumes", new JSONArray());
            jo1.put("throughs", new JSONArray());

        } catch (Exception ignored) {
            ;
        }
        return jo1;
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

    protected static void put(HandlerUnit newUnit, Class<?> cla) {
        List<HandlerUnit> units;
        if (!flowUnitsMap.containsKey(cla)) {
            units = new LinkedList<>();
            units.add(new HandlerUnit(null, Float.MAX_VALUE, null));
            flowUnitsMap.put(cla, units);
        } else
            units = flowUnitsMap.get(cla);

        int index = 0;
        for (; index < units.size(); index++) {
            HandlerUnit unit = units.get(index);
            //不允许order相等
            if (unit.getOrder().equals(newUnit.getOrder()))
                return;
            if (unit.getOrder() > newUnit.getOrder())
                break;
        }
        float tmp = index > 0 ? newUnit.getOrder() - units.get(index - 1).getOrder() : 0;
        if (0 < tmp && tmp <= 0.1)
            units.set(index - 1, newUnit);
        else
            units.add(index, newUnit);
    }

    protected static List<HandlerUnit> get(Class<?> flow) {
        return flowUnitsMap.get(flow);
    }

    public static void outToJson() {
        outToJson(filePath);
    }

    public static void outToJson(String path) {
        filePath = path;
        flowUnitsMap.entrySet().stream().forEach(
                flowUnits -> {
                    Class<?> cla = flowUnits.getKey();
                    List<HandlerUnit> units = flowUnits.getValue();
                    JSONObject jo = new JSONObject();
                    jo.put("simpleClass", cla.getSimpleName());
                    jo.put("fullClass", cla.getName());
                    JSONArray info = new JSONArray();
                    jo.put("info", info);

                    info.add(readDefault.apply(cla));

                    for (int i = 0; i < units.size() - 1; i++) {
                        HandlerUnit handlerUnit = units.get(i);
                        info.add(readHandlerUnit.apply(handlerUnit));
                    }
                    fileOutput.apply(jo);
                }
        );
        filePath = "schema.json";
    }
}
