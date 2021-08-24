package com.lordsl.unit.common;

import com.lordsl.unit.common.node.Node;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;

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

    static List<Class<? extends NodeModel>> getAllHandlerImpl(String pkgName) {
        return scanInterfaceImplInPkg(pkgName, NodeModel.class);
    }

    static Node getNode(Class<? extends NodeModel> target) {
        return Dictator.getAllNodes().stream()
                .filter(item -> item.getNodeModel().getClass().equals(target))
                .findAny()
                .orElse(null);
    }


}
