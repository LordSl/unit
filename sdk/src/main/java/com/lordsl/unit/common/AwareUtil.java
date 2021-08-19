package com.lordsl.unit.common;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class AwareUtil {

    private static <T> List<Class<? extends T>> scanInterfaceImplInPkg(String pkgName, Class<? extends T> t) {
        ClassInfoList classInfoList = new ClassGraph().enableClassInfo().scan().getAllClasses();
        return classInfoList.stream()
                .filter(classInfo -> classInfo.getName().startsWith(pkgName))
                .map(classInfo -> {
                    try {
                        Class<? extends T> cla = Class.forName(classInfo.getName()).asSubclass(t);
                        return cla;
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    static List<Class<? extends HandlerModel>> getAllHandlerImpl(String pkgName) {
        return scanInterfaceImplInPkg(pkgName, HandlerModel.class);
    }

    static List<Class<? extends FlowModel>> getAllFlowImpl(String pkgName) {
        return scanInterfaceImplInPkg(pkgName, FlowModel.class);
    }

    static Node getNode(Class<? extends HandlerModel> target) {
        return Dictator.getFlowNodesMap().values().stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getCla().equals(target))
                .findAny()
                .orElse(null);
    }

    static void softInitHandlers(List<HandlerModel> handlers) {
        handlers.forEach(model -> TaskResolver.addHandlerInitTask(Adapter.getHandlerInitTask(Mode.simple, model)));
    }

    static void softInitFlows(List<FlowModel> flows) {
        flows.forEach(model -> TaskResolver.addFlowInitTask(Adapter.getFlowInitTask(Mode.simple, model)));
    }

}
