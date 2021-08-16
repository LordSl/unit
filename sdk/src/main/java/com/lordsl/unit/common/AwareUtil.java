package com.lordsl.unit.common;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class AwareUtil {

    private static <T> List<Class<? extends T>> scanInterfaceImplByPeer(Class<?> peerClass, Class<? extends T> t) {
        File pkgDicFile = new File(Objects.requireNonNull(peerClass.getResource("")).getPath());
        String pkgName = peerClass.getPackage().getName();
        if (!pkgDicFile.isDirectory()) {
            Info.PurpleAlert("scan stop, pkg not found");
            return null;
        }
        List<String> claFullNames = Arrays.stream(Objects.requireNonNull(pkgDicFile.listFiles()))
                .map(File::getName)
                .filter(name -> name.endsWith(".class"))
                .map(name -> pkgName + "." + name.replace(".class", ""))
                .collect(Collectors.toList());
        return claFullNames.stream()
                .map(name -> {
                    try {
                        Class<? extends T> cla = Class.forName(name).asSubclass(t);
                        return cla;
                    } catch (Exception ignored) {
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static <T> List<Class<? extends T>> scanInterfaceImplInPkg(String pkgName, Class<? extends T> t) {
        String rootDir = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String relativeDir = pkgName.replace(".", "/");
        File pkgDicFile = new File(rootDir + relativeDir);
        if (!pkgDicFile.isDirectory()) {
            Info.PurpleAlert("scan stop, pkg not found");
            return null;
        }
        List<String> claFullNames = Arrays.stream(Objects.requireNonNull(pkgDicFile.listFiles()))
                .map(File::getName)
                .filter(name -> name.endsWith(".class"))
                .map(name -> pkgName + "." + name.replace(".class", ""))
                .collect(Collectors.toList());
        return claFullNames.stream()
                .map(name -> {
                    try {
                        Class<? extends T> cla = Class.forName(name).asSubclass(t);
                        return cla;
                    } catch (Exception ignored) {
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    static List<Class<? extends HandlerModel>> getAllHandlerImpl(Class<?> peer) {
        return scanInterfaceImplByPeer(peer, HandlerModel.class);
    }

    static List<Class<? extends FlowModel>> getAllFlowImpl(Class<?> peer) {
        return scanInterfaceImplByPeer(peer, FlowModel.class);
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
