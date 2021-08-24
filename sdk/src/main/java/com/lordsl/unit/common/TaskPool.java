package com.lordsl.unit.common;

import java.util.HashSet;
import java.util.Set;

public class TaskPool {

    private static final Set<Runnable> referTasks = new HashSet<>();
    private static final Set<Runnable> handlerInitTasks = new HashSet<>();
    private static final Set<Runnable> flowInitTasks = new HashSet<>();

    public static void addReferTask(Runnable task) {
        referTasks.add(task);
    }

    public static void resolveReferTasks() {
        referTasks.forEach(Runnable::run);
    }

    public static void addHandlerInitTask(Runnable task) {
        handlerInitTasks.add(task);
    }

    public static void resolveHandlerInitTasks() {
        handlerInitTasks.forEach(Runnable::run);
    }

    public static void addFlowInitTask(Runnable task) {
        flowInitTasks.add(task);
    }

    public static void resolveFlowInitTasks() {
        flowInitTasks.forEach(Runnable::run);
    }

}
