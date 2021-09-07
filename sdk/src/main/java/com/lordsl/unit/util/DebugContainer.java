package com.lordsl.unit.util;

public class DebugContainer extends Container {

    public DebugContainer() {
    }

    public DebugContainer(Container container) {
        container.map.forEach(map::put);
    }

    @Override
    public <T> void put(String name, T input) {
        if (map.containsKey(name))
            Info.PurpleAlert(String.format("merge where k = %s v = %s", name, input.toString()));
        else
            Info.BlueInfo(String.format("put where k = %s v = %s", name, input.toString()));
        map.put(name, input);
    }

    @Override
    public <T> T get(String name) {
        if (!map.containsKey(name)) {
            Info.PurpleAlert(String.format("no k = %s", name));
            return null;
        } else {
            T t = (T) map.get(name);
            Info.BlueInfo(String.format("get where k = %s v = %s", name, t));
            return t;
        }
    }

    @Override
    public <T> T fetch(String name, Class<? extends T> cla) {
        if (!map.containsKey(name)) {
            Info.PurpleAlert(String.format("no k = %s", name));
            return null;
        } else {
            try {
                T t = cla.cast(map.get(name));
                Info.BlueInfo(String.format("get where k = %s v = %s", name, t));
                return t;
            } catch (Exception ignored) {
                Info.BlueInfo(String.format("type convert fail where k = %s", name));
                return null;
            }
        }
    }

    @Override
    public <T> void remove(String name) {
        if (!map.containsKey(name))
            Info.PurpleAlert(String.format("no k-v where k = %s", name));
        else {
            Info.BlueInfo(String.format("rm k-v where k = %s", name));
            map.remove(name);
        }
    }
}
