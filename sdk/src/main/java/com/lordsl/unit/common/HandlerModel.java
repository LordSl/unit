package com.lordsl.unit.common;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

public interface HandlerModel {
    default Supplier<HandlerModel> getTemplate() {
        try {
            Constructor<? extends HandlerModel> constructor = this.getClass().getDeclaredConstructor();
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

    class Stand {
        public static void init(HandlerModel model) {
            if (Signal.regisEnable())
                TaskResolver.addHandlerInitTask(Adapter.getHandlerInitTask(Mode.simple, model));
        }
    }

}
