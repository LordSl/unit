package com.lordsl.unit.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FuncUtil {

    public static <T> void forColl(Collection<T> coll, Consumer<T> doIfExist, Runnable doIfNotExist) {
        if (null != coll && !coll.isEmpty()) {
            if (null == doIfExist)
                doIfExist = t -> {
                };
            if (null == doIfNotExist)
                doIfNotExist = () -> {
                };
            for (T t : coll) {
                if (null != t)
                    doIfExist.accept(t);
                else
                    doIfNotExist.run();
            }
        }
    }

    /**
     * not concurrent safe
     */
    public static <K, V> void forMapKey(Map<K, V> map, K key, BiConsumer<K, V> doIfExist, BiConsumer<Map<K, V>, K> doIfNotExist) {
        if (null != map) {
            if (map.containsKey(key) && null != doIfExist) {
                V val = map.get(key);
                doIfExist.accept(key, val);
            } else if (null != doIfNotExist) {
                doIfNotExist.accept(map, key);
            }
        }
    }

    public static <T> void forVar(T t, Consumer<T> doIfExist, Runnable doIfNotExist) {
        if (null != t && null != doIfExist) {
            doIfExist.accept(t);
        } else if (null != doIfNotExist) {
            doIfNotExist.run();
        }
    }

    public static <T, R> R retDefaultIfEx(T actualValue, R defaultValue, Function<T, R> compute) {
        try {
            return compute.apply(actualValue);
        } catch (Throwable throwable) {
            return defaultValue;
        }
    }

    public static <T> boolean allMatch(Predicate<T> predicate, T... items) {
        for (T item : items) {
            if (!predicate.test(item)) return false;
        }
        return true;
    }

    public static <T> boolean anyMatch(Predicate<T> predicate, T... items) {
        for (T item : items) {
            if (predicate.test(item)) return true;
        }
        return false;
    }

}
