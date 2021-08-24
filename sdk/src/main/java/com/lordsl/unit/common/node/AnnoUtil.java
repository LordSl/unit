package com.lordsl.unit.common.node;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnnoUtil {

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
}
