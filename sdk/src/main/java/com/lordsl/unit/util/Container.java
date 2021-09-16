package com.lordsl.unit.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Container {
    protected final Map<Object, Object> map = new ConcurrentHashMap<>();

    public static KeyWait builder() {
        Container container = new Container();
        KeyWait keyWait = new KeyWait();
        ValueWait valueWait = new ValueWait();
        keyWait.valueWait = valueWait;
        valueWait.keyWait = keyWait;
        valueWait.container = container;
        return keyWait;
    }

    public <T> void put(Object key, T input) {
        map.put(key, input);
    }

    /**
     * unsafe method
     * possible to throw ClassCastException when shot wrong key
     */
    public <T> T get(Object key) {
        return (T) map.get(key);
    }

    public <T> void remove(Object key) {
        map.remove(key);
    }

    /**
     * return null when shot wrong key
     */
    public <T> T fetch(Object key, Class<? extends T> cla) {
        try {
            return cla.cast(map.get(key));
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
        private Object key;

        private KeyWait() {
        }

        public ValueWait key(Object key) {
            this.key = key;
            return valueWait;
        }

        public Container build() {
            return valueWait.container;
        }
    }
}
