package com.lordsl.unit.common.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TaskPool {
    private final Map<String, Set<Runnable>> pool = new HashMap<>();

    public void put(String name, Runnable task) {
        Set<Runnable> subPool = pool.merge(name, new HashSet<>(), (o, n) -> o);
        subPool.add(task);
    }

    public void resolve(String name) {
        pool.getOrDefault(name, new HashSet<>()).forEach(Runnable::run);
    }

    public Set<Runnable> get(String name) {
        return pool.getOrDefault(name, new HashSet<>());
    }

    public void remove(String name) {
        pool.remove(name);
    }
}
