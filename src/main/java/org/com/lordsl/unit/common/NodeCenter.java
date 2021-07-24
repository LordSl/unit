package org.com.lordsl.unit.common;

import org.com.lordsl.unit.common.anno.Unit;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NodeCenter {
    static void regisSimple(HandlerModel model) {
        SimpleMode.regis(model);
    }

    static List<Function<Container, Container>> buildSimple(FlowModel model) {
        return SimpleMode.build(model);
    }

    private static class SimpleMode {
        //新建一个该对象，即完成了一次注册，生成一个或多个Node到Dictator中

        private static void regis(HandlerModel model) {

            Class<?> cla = model.getClass();
            Unit unit = cla.getAnnotation(Unit.class);
            if (unit == null) return;

            List<Float> orders = Arrays.stream(unit.order()).map(Float::parseFloat).collect(Collectors.toList());
            List<Class<?>> flows = Arrays.stream(unit.flow()).collect(Collectors.toList());

            int len = orders.size();
            if (len != flows.size())
                return;

            Function<Container, Container> func = PublicFunc.getConductFunction.apply(cla);
            for (int index = 0; index < len; index++) {
                Class<?> flow = flows.get(index);
                Float order = orders.get(index);
                Node node = new Node(order, cla, func);
                Dictator.put(flow, node);
            }
        }

        //从上层类获取方法组成的执行链
        private static List<Function<Container, Container>> build(FlowModel model) {
            List<Node> nodes = Dictator.get(model.getClass());
            List<Function<Container, Container>> res = new LinkedList<>();
            for (int i = 0; i < nodes.size() - 1; i++) {
                res.add(nodes.get(i).getFunction());
            }
            return res;
        }
    }
}
