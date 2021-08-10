package com.lordsl.unit.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PublicFunc {

    final static Function<Map<String, Field>, Map<String, Class<?>>> convertMap = (map) -> Stream.of(
            map.entrySet())
            .flatMap(Collection::stream)
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    set -> set.getValue().getType()
            ));
    final static BiFunction<Class<? extends Annotation>, Class<?>, Set<Method>> getAnnoMethods = (annoCla, objCla) -> {
        Set<Method> set = new HashSet<>();
        Arrays.stream(objCla.getDeclaredMethods()).forEach(
                method -> {
                    Annotation anno = method.getAnnotation(annoCla);
                    if (null != anno) {
                        set.add(method);
                    }
                }
        );
        return set;
    };
    final static BiFunction<Class<? extends Annotation>, Class<?>, Map<String, Field>> getAnnoFields = (annoCla, objCla) -> {
        Map<String, Field> items = new HashMap<>();
        try {
            Method target = annoCla.getDeclaredMethod("name");
            Arrays.stream(objCla.getDeclaredFields()).forEach(
                    field -> {
                        Annotation anno = field.getAnnotation(annoCla);
                        try {
                            String name = (String) target.invoke(anno);
                            if (null != anno) {
                                items.put(name.equals("") ? field.getName() : name, field);
                            }
                        } catch (Exception ignored) {
                        }
                    }
            );
        } catch (Exception ignored) {
        }
        return items;
    };
    final static Function<Class<?>, Boolean> isReference = cla -> {
        if (cla.isPrimitive()) return false;
        try {
            if (((Class<?>) (cla.getField("TYPE").get(null))).isPrimitive())
                return false;
        } catch (Exception ignored) {
        }
        return true;
    };
}
