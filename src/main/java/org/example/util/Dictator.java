package org.example.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Dictator {
    private static final Map<Class<?>, List<HandlerUnit>> flowUnitsMap = new HashMap<>();

    public static void put(HandlerUnit newUnit, Class<?> cla) {
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

    public static List<HandlerUnit> get(Class<?> flow) {
        return flowUnitsMap.get(flow);
    }
}
