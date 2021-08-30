package com.lordsl.unit.util;

import java.util.HashMap;
import java.util.Map;

public class Container {
    protected final Map<String, Object> map = new HashMap<>();

    public <T> void put(T input) {
        String name = input.getClass().getName();
        put(name, input);
    }

    public <T> void put(String name, T input) {
        map.put(name, input);
    }

    public <T> T get(T output) {
        String name = output.getClass().getName();
        return get(name);
    }

    public <T> T get(String name) {
        return (T) map.get(name);
    }

    public <T> void remove(T delete) {
        remove(delete.getClass().getName());
    }

    public <T> void remove(String name) {
        map.remove(name);
    }

}
