package com.lordsl.unit.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParseUtil {

    public static <T extends Annotation> Map<Field, T> getFieldAnnoMap(Class<T> annoCla, Class<?> objCla) {
        Map<Field, T> map = new HashMap<>();
        Arrays.stream(objCla.getDeclaredFields()).forEach(
                field -> {
                    T anno = field.getAnnotation(annoCla);
                    if (null != anno) {
                        map.put(field, anno);
                    }
                }
        );
        return map;
    }

    public static <T extends Annotation> Map<Method, T> getMethodAnnoMap(Class<T> annoCla, Class<?> objCla) {
        Map<Method, T> map = new HashMap<>();
        Arrays.stream(objCla.getDeclaredMethods()).forEach(
                method -> {
                    T anno = method.getAnnotation(annoCla);
                    if (null != anno) {
                        map.put(method, anno);
                    }
                }
        );
        return map;
    }

    public static <T extends Annotation> Map<String, Field> mapByName(Map<Field, T> sourceMap) {
        Map<String, Field> map = new HashMap<>();
        Optional<T> annoExample = sourceMap.values().stream().findAny();
        if (!annoExample.isPresent()) return map;
        try {
            Method target = annoExample.get().getClass().getDeclaredMethod("name");
            sourceMap.forEach(
                    (field, anno) -> {
                        try {
                            String name = (String) target.invoke(anno);
                            if (null != anno) {
                                map.put(name.equals("") ? field.getName() : name, field);
                            }
                        } catch (Exception ignored) {
                        }
                    }
            );
        } catch (Exception ignored) {
        }
        return map;
    }

    public static <T extends Annotation> Map<String, Field> parseField(Class<T> annoCla, Class<?> objCla) {
        return mapByName(getFieldAnnoMap(annoCla, objCla));
    }

    public static Boolean isReferenceExceptString(Field field) {
        Class<?> cla = field.getType();
        return isReferenceExceptString(cla);
    }

    public static Boolean isReferenceExceptString(Class<?> cla) {
        if (cla.equals(String.class)) return false;
        if (cla.isPrimitive()) return false;
        try {
            if (((Class<?>) (cla.getField("TYPE").get(null))).isPrimitive())
                return false;
        } catch (Exception ignored) {
        }
        return true;
    }
}
