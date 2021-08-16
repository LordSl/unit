package com.lordsl.unit.common;

class Adapter {
    static Runnable DoNothing = () -> {
    };

    static Runnable getHandlerInitTask(Mode mode, HandlerModel model) {
        if (mode.equals(Mode.simple)) {
            return SimpleMode.getHandlerInitTask(model);
        }
        return DoNothing;
    }

    static Runnable getFlowInitTask(Mode mode, FlowModel model) {
        if (mode.equals(Mode.simple)) {
            return SimpleMode.getFlowInitTask(model);
        }
        return DoNothing;
    }

    static Runnable getFinalDoneTask(Mode mode) {
        if (mode.equals(Mode.simple)) {
            return SimpleMode.getFinalDoneTask();
        }
        return DoNothing;
    }

    private static class SimpleMode {
        private static Runnable getHandlerInitTask(HandlerModel model) {
            return () -> new NodeResolver(model).resolve();
        }

        private static Runnable getFlowInitTask(FlowModel model) {
            return () -> Dictator.polyToFinalFunction(model.getClass());
        }

        private static Runnable getFinalDoneTask() {
            return () -> {
                TaskResolver.resolveHandlerInitTasks();
                TaskResolver.resolveReferTasks();
                TaskResolver.resolveFlowInitTasks();
            };
        }

    }

}
