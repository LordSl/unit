package com.lordsl.unit.common;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AwareUtil {

    private static <T> List<Class<? extends T>> scanInterfaceImplInPkg(String pkgName, Class<? extends T> t) {
        String rootDir = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        rootDir = rootDir.replace("/test-classes/", "/classes/");
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

    static List<Class<? extends HandlerModel>> getAllHandlerImpl(String pkgName) {
        return scanInterfaceImplInPkg(pkgName, HandlerModel.class);
    }

    static List<Class<? extends FlowModel>> getAllFlowImpl(String pkgName) {
        return scanInterfaceImplInPkg(pkgName, FlowModel.class);
    }

    static void forceInitHandlers(List<Class<? extends HandlerModel>> list) {
        list.forEach(
                cla -> {
                    try {
                        Constructor<?> con = cla.getConstructor();
                        con.setAccessible(true);
                        HandlerModel model;
                        synchronized (Signal.class) {
                            Signal.setOff();
                            model = (HandlerModel) con.newInstance();
                            Signal.setOn();
                        }
                        model = model.getTemplate().apply(null);
                        HandlerModel.Stand.init(model);
                    } catch (Exception e) {
                        Info.PurpleAlert("handlers force init fail");
                    }
                }
        );
    }

    static void forceInitFlows(List<Class<? extends FlowModel>> list) {
        list.forEach(
                cla -> {
                    try {
                        Constructor<?> con = cla.getConstructor();
                        con.setAccessible(true);
                        FlowModel model = (FlowModel) con.newInstance();
                        FlowModel.Stand.lazyInit(model);
                    } catch (Exception e) {
                        Info.PurpleAlert("flows force init fail");
                    }
                }
        );
    }

    static Node getNode(Class<? extends HandlerModel> target) {
        return Dictator.getFlowNodesMap().values().stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getCla().equals(target))
                .findAny()
                .orElse(null);
    }

    static Function<Container, Container> getFunc(Class<? extends HandlerModel> target) {
        return getNode(target).getFunction();
    }
}
