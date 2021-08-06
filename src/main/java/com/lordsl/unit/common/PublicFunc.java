package com.lordsl.unit.common;

import com.lordsl.unit.common.anno.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
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

    final static Function<Class<?>, Set<Method>> getHandlesSet = (Class<?> cla) -> {
        Set<Method> set = new HashSet<>();
        Arrays.stream(cla.getDeclaredMethods()).forEach(
                method -> {
                    Handle item = method.getAnnotation(Handle.class);
                    if (null != item) {
                        set.add(method);
                    }
                }
        );
        return set;
    };

    final static Function<Class<?>, Map<String, Field>> getProducesFields = (Class<?> cla) -> {
        Map<String, Field> items = new HashMap<>();
        Arrays.stream(cla.getDeclaredFields()).forEach(
                field -> {
                    Produce item = field.getAnnotation(Produce.class);
                    if (null != item) {
                        items.put(item.name().equals("") ? field.getName() : item.name(), field);
                    }
                }
        );
        return items;
    };

    final static Function<Class<?>, Map<String, Field>> getConsumesFields = (Class<?> cla) -> {
        Map<String, Field> items = new HashMap<>();
        Arrays.stream(cla.getDeclaredFields()).forEach(
                field -> {
                    Consume item = field.getAnnotation(Consume.class);
                    if (null != item) {
                        items.put(item.name().equals("") ? field.getName() : item.name(), field);
                    }
                }
        );
        return items;
    };

    final static Function<Class<?>, Map<String, Field>> getThroughsFields = (Class<?> cla) -> {
        Map<String, Field> items = new HashMap<>();
        Arrays.stream(cla.getDeclaredFields()).forEach(
                field -> {
                    Through item = field.getAnnotation(Through.class);
                    if (null != item) {
                        items.put(item.name().equals("") ? field.getName() : item.name(), field);
                    }
                }
        );
        return items;
    };

    final static Function<Class<?>, Map<String, Field>> getRefersFields = (Class<?> cla) -> {
        Map<String, Field> items = new HashMap<>();
        Arrays.stream(cla.getDeclaredFields()).forEach(
                field -> {
                    Refer item = field.getAnnotation(Refer.class);
                    if (null != item) {
                        items.put(item.name().equals("") ? field.getName() : item.name(), field);
                    }
                }
        );
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
