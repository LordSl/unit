package com.lordsl.unit.common.util;

import java.util.HashMap;
import java.util.Map;

public class DebugContainer extends Container {
    private final Map<String, Object> map = new HashMap<String, Object>();

    public <T> void put(T input) {
        String name = input.getClass().getName();
        put(name, input);
    }

    public <T> void put(String name, T input) {
        if (map.containsKey(name))
            Info.PurpleAlert(String.format("merge k-v where k = %s", name));
        else
            Info.BlueInfo(String.format("put k-v where k = %s", name));
        map.put(name, input);
    }

    public <T> T get(T output) {
        String name = output.getClass().getName();
        return get(name);
    }

    public <T> T get(String name) {
        if (!map.containsKey(name))
            Info.PurpleAlert(String.format("no k-v where k = %s", name));
        else {
            Info.BlueInfo(String.format("get k-v where k = %s", name));
            try {
                return (T) map.get(name);
            } catch (ClassCastException e) {
                Info.GreenLog("type convert fail");
            }
        }
        return null;
    }

    public <T> void remove(T delete) {
        remove(delete.getClass().getName());
    }

    public <T> void remove(String name) {
        if (!map.containsKey(name))
            Info.PurpleAlert(String.format("no k-v where k = %s", name));
        else {
            Info.BlueInfo(String.format("rm k-v where k = %s", name));
            map.remove(name);
        }
    }
}
