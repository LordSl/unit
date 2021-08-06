package com.lordsl.unit.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Adapter {
    static void regisSimple(HandlerModel model) {
        SimpleMode.regis(model);
    }

    static List<Function<Container, Container>> buildSimple(FlowModel model) {
        return SimpleMode.build(model);
    }

    private static class SimpleMode {
        private static void regis(HandlerModel model) {
            new Resolver(model).resolve();
        }

        private static List<Function<Container, Container>> build(FlowModel model) {
            List<Node> nodes = Dictator.getNodes(model.getClass());
            if (Signal.isOn()) ReferInjectManager.resolveAll();
            List<Function<Container, Container>> res = new ArrayList<>();
            for (Node node : nodes)
                res.add(node.getFunction());
            return res;
        }
    }

}
