package com.lordsl.unit.common;

import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Adapter {
    static void regisSimple(HandlerModel model) {
        SimpleMode.regis(model);
    }

    static List<Function<Container, Container>> buildSimple(FlowModel model) {
        return SimpleMode.build(model);
    }

    private static class SimpleMode {
        private static void regis(HandlerModel model) {

            Class<?> cla = model.getClass();
            Unit unit = cla.getAnnotation(Unit.class);
            if (unit == null) return;

            List<Float> orders = Arrays.stream(unit.unis()).map(item -> Float.parseFloat(item.order())).collect(Collectors.toList());
            List<Class<?>> flows = Arrays.stream(unit.unis()).map(Uni::flow).collect(Collectors.toList());

            if (orders.size() != flows.size())
                return;

            Function<Container, Container> func = PublicFunc.getConductFunction.apply(model);
            for (int index = 0; index < orders.size(); index++) {
                Class<?> flow = flows.get(index);
                Float order = orders.get(index);
                Node node = new Node(order, model, func);
                Dictator.putNode(flow, node);
            }
        }

        private static List<Function<Container, Container>> build(FlowModel model) {
            List<Node> nodes = Dictator.getNodes(model.getClass());
            for (Node node : nodes) {
                PublicFunc.getRefersFields
                        .apply(node.getModel().getClass())
                        .forEach((name, field) -> {
                            try {
                                field.setAccessible(true);
                                Dictator.putRefer(name, field.get(node.getModel()));
                            } catch (Exception e) {
                                Info.PurpleAlert("refer inject exception");
                            }
                        });
            }

            List<Function<Container, Container>> res = new ArrayList<>();
            for (Node node : nodes)
                res.add(node.getFunction());
            return res;
        }
    }

}
