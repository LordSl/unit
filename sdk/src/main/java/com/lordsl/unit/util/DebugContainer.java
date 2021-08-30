package com.lordsl.unit.util;

public class DebugContainer extends Container {

    @Override
    public <T> void put(String name, T input) {
        if (map.containsKey(name))
            Info.PurpleAlert(String.format("merge k-v where k = %s", name));
        else
            Info.BlueInfo(String.format("put k-v where k = %s", name));
        map.put(name, input);
    }

    @Override
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
