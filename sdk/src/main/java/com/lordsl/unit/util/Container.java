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

    public static KeyWait builder() {
        Container container = new Container();
        KeyWait keyWait = new KeyWait();
        ValueWait valueWait = new ValueWait();
        keyWait.valueWait = valueWait;
        valueWait.keyWait = keyWait;
        valueWait.container = container;
        return keyWait;
    }

    /**
     * unsafe method
     * possible to throw ClassCastException when shot wrong key
     */
    public <T> T get(String name) {
        return (T) map.get(name);
    }

    public <T> void remove(T delete) {
        remove(delete.getClass().getName());
    }

    public <T> void remove(String name) {
        map.remove(name);
    }

    /**
     * return null when shot wrong key
     */
    public <T> T fetch(String name, Class<? extends T> cla) {
        try {
            return cla.cast(map.get(name));
        } catch (Exception ignored) {
            return null;
        }
    }

    public KeyWait getBuilder() {
        KeyWait keyWait = new KeyWait();
        ValueWait valueWait = new ValueWait();
        keyWait.valueWait = valueWait;
        valueWait.keyWait = keyWait;
        valueWait.container = this;
        return keyWait;
    }

    @Override
    public String toString() {
        return String.format("Container@%s{map=%s}", hashCode(), map);
    }

    public static class ValueWait {
        private KeyWait keyWait;
        private Container container;

        private ValueWait() {
        }

        public <T> KeyWait val(T val) {
            container.put(keyWait.key, val);
            return keyWait;
        }
    }

    public static class KeyWait {
        private ValueWait valueWait;
        private String key;

        private KeyWait() {
        }

        public ValueWait key(String name) {
            key = name;
            return valueWait;
        }

        public Container build() {
            return valueWait.container;
        }
    }
}
