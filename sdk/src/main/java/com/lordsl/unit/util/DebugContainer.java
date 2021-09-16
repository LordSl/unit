package com.lordsl.unit.util;

public class DebugContainer extends Container {

    public DebugContainer() {
    }

    public DebugContainer(Container container) {
        container.map.forEach(map::put);
    }

    @Override
    public <T> void put(Object key, T input) {
        if (map.containsKey(key))
            Info.PurpleAlert(String.format("merge where k = %s v = %s", key, input.toString()));
        else
            Info.BlueInfo(String.format("put where k = %s v = %s", key, input.toString()));
        map.put(key, input);
    }

    @Override
    public <T> T get(Object key) {
        if (!map.containsKey(key)) {
            Info.PurpleAlert(String.format("no k = %s", key));
            return null;
        } else {
            T t = (T) map.get(key);
            Info.BlueInfo(String.format("get where k = %s v = %s", key, t));
            return t;
        }
    }

    @Override
    public <T> T fetch(Object key, Class<? extends T> cla) {
        if (!map.containsKey(key)) {
            Info.PurpleAlert(String.format("no k = %s", key));
            return null;
        } else {
            try {
                T t = cla.cast(map.get(key));
                Info.BlueInfo(String.format("get where k = %s v = %s", key, t));
                return t;
            } catch (Exception ignored) {
                Info.BlueInfo(String.format("type convert fail where k = %s", key));
                return null;
            }
        }
    }

    @Override
    public <T> void remove(Object key) {
        if (!map.containsKey(key))
            Info.PurpleAlert(String.format("no k-v where k = %s", key));
        else {
            Info.BlueInfo(String.format("rm k-v where k = %s", key));
            map.remove(key);
        }
    }
}
