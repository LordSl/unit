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

    void handle();

    class Stand {
        public static void init(HandlerModel model) {
            if (Signal.isOn())
                Adapter.regisSimple(model);
        }

        public static void setNext(Class<?>... handlers) {
            //通过线程id的map和flow通信
        }

        public static void call(Class<?> handlers) {
            //通过dictator直接找到
        }
    }

}
