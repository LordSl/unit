package com.lordsl.unit.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParseUtil {

    static Map<String, Class<?>> convertFiledMapToClassMap(Map<String, Field> map) {
        return Stream.of(
                map.entrySet())
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        set -> set.getValue().getType()
                ));
    }

    static Set<Method> getAnnoMethods(Class<? extends Annotation> annoCla, Class<?> objCla) {
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
    }

    static Map<String, Field> getAnnoFields(Class<? extends Annotation> annoCla, Class<?> objCla) {
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
    }

    static Boolean isReference(Class<?> cla) {
        if (cla.isPrimitive()) return false;
        try {
            if (((Class<?>) (cla.getField("TYPE").get(null))).isPrimitive())
                return false;
        } catch (Exception ignored) {
        }
        return true;
    }
}
