package org.com.lordsl.unit.common;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Container {
    private Logger log = Logger.getLogger("test");
    private Map<String, Object> map = new HashMap<String, Object>();

    public <T> void put(T input) {
        String name = input.getClass().getName();
        put(name, input);
    }

    public <T> void put(String name, T input) {
        if (map.containsKey(name))
            log.info(String.format("merge k-v where k = %s", name));
        else
            log.info(String.format("put k-v where k = %s", name));
        map.put(name, input);
    }

    public <T> T get(T output) {
        String name = output.getClass().getName();
        return get(name);
    }

    public <T> T get(String name) {
        if (!map.containsKey(name))
            log.info(String.format("no k-v where k = %s", name));
        else {
            log.info(String.format("get k-v where k = %s", name));
            try {
                return (T) map.get(name);
            } catch (ClassCastException e) {
                log.info("type convert fail");
            }
        }
        return null;
    }

    public <T> void remove(T delete) {
        remove(delete.getClass().getName());
    }

    public <T> void remove(String name) {
        if (!map.containsKey(name))
            log.info(String.format("no k-v where k = %s", name));
        else {
            log.info(String.format("rm k-v where k = %s", name));
            map.remove(name);
        }
    }

}
