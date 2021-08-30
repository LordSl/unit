package com.lordsl.unit.common;

import com.lordsl.unit.util.TaskPool;

import java.util.Set;

public class DefaultTaskPool {

    private static TaskPool inner = new TaskPool();

    public static void setInner(TaskPool taskPool) {
        inner = taskPool;
    }

    public static void put(String name, Runnable task) {
        inner.put(name, task);
    }

    public static void resolve(String name) {
        inner.resolve(name);
    }

    public static Set<Runnable> get(String name) {
        return inner.get(name);
    }

    public static void remove(String name) {
        inner.remove(name);
    }
}
