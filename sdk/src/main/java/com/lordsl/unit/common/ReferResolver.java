package com.lordsl.unit.common;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class ReferResolver {

    private static final Set<Function<Void, Void>> queue = new HashSet<>();

    static void addTask(Function<Void, Void> func) {
        queue.add(func);
    }

    static void resolveAll() {
        queue.forEach(func ->
                func.apply(null)
        );
    }

}
