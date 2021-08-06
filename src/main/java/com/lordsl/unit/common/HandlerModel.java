package com.lordsl.unit.common;

import java.util.function.Function;

public interface HandlerModel {
    default Function<Void, HandlerModel> getTemplate() {
        Function<Void, HandlerModel> res;
        res = (Void) -> {
            try {
                return this.getClass().newInstance();
            } catch (Exception ignored) {
                ;
            }
            return null;
        };
        return res;
    }

    class Stand {
        public static void init(HandlerModel model) {
            if (Signal.isOn())
                Adapter.regisSimple(model);
        }
    }

}
