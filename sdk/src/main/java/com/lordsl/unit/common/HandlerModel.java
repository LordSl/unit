package com.lordsl.unit.common;

import java.lang.reflect.Constructor;
import java.util.function.Function;

public interface HandlerModel {
    default Function<Void, HandlerModel> getTemplate() {
        try {
            Constructor<? extends HandlerModel> constructor = this.getClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            return (Void) -> {
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

    class Stand {
        public static void init(HandlerModel model) {
            if (Signal.regisEnable())
                Adapter.regisSimple(model);
        }
    }

}
