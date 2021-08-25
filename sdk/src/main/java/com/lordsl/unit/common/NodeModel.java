package com.lordsl.unit.common;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

/**
 * 基本单元，Node有两种身份，handler和flow
 * Node(handler) * n -> Node(flow)
 * 一个Node可以同时是handler和flow
 */
public interface NodeModel {

    default Supplier<NodeModel> getTemplate() {
        try {
            Constructor<? extends NodeModel> constructor = this.getClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            return () -> {
                try {
                    return constructor.newInstance();
                } catch (Exception ignored) {
                    return null;
                }
            };
        } catch (Exception ignored) {
            return null;
        }
    }

}
