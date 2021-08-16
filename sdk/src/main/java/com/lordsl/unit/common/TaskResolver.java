package com.lordsl.unit.common;

import java.util.HashSet;
import java.util.Set;

class TaskResolver {

    private static final Set<Runnable> referTasks = new HashSet<>();
    private static final Set<Runnable> handlerInitTasks = new HashSet<>();
    private static final Set<Runnable> flowInitTasks = new HashSet<>();

    static void addReferTask(Runnable task) {
        referTasks.add(task);
    }

    static void resolveReferTasks() {
        referTasks.forEach(Runnable::run);
    }

    static void addHandlerInitTask(Runnable task) {
        handlerInitTasks.add(task);
    }

    static void resolveHandlerInitTasks() {
        handlerInitTasks.forEach(Runnable::run);
    }

    static void addFlowInitTask(Runnable task) {
        flowInitTasks.add(task);
    }

    static void resolveFlowInitTasks() {
        flowInitTasks.forEach(Runnable::run);
    }

}
