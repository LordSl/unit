package com.lordsl.unit.common;

import com.lordsl.unit.common.node.Node;
import com.lordsl.unit.util.Container;
import com.lordsl.unit.util.Info;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Dictator {
    private static final Map<Class<? extends NodeModel>, List<Node>> flowHandlerListMap = new HashMap<>();

    private final static Container referContainer = new Container();

    private final static Map<Class<? extends NodeModel>, Function<Container, Container>> flowConductFunctionMap = new HashMap<>();

    private final static Map<Class<? extends NodeModel>, Function<Container, Container>> handlerConductFunctionMap = new HashMap<>();

    public static <T> void putRefer(String name, T t) {
        referContainer.put(name, t);
    }

    public static <T> T getRefer(String name) {
        return referContainer.get(name);
    }

    public static void regisNode(Node node) {
        List<Node> nodes;
        Class<? extends NodeModel> flow = node.getFlow();
        if (!flowHandlerListMap.containsKey(flow)) {
            nodes = new LinkedList<>();
            Node barrier = new Node();
            barrier.setOrder(Float.MAX_VALUE);
            nodes.add(barrier);
            flowHandlerListMap.put(flow, nodes);
        } else
            nodes = flowHandlerListMap.get(flow);

        int index = 0;
        for (; index < nodes.size(); index++) {
            Node thisNode = nodes.get(index);
            if (thisNode.getOrder().equals(node.getOrder()))
                return;
            if (thisNode.getOrder() > node.getOrder())
                break;
        }
        float tmp = index > 0 ? node.getOrder() - nodes.get(index - 1).getOrder() : -1;
        if (0 < tmp && tmp <= 0.1) {
            nodes.set(index - 1, node);
            Info.BlueInfo(String.format("node from model 「%s」 merge node from model 「%s」", node.getNodeModel(), nodes.get(index - 1).getClass().getName()));
        } else {
            nodes.add(index, node);
        }
        Info.BlueInfo(String.format("node from model 「%s」 regis in node 「%s」", node.getNodeModel(), node.getFlow().getName()));
        handlerConductFunctionMap.put(node.getNodeModel().getClass(), node.getConductFunction());
    }

    static void buildFlowConductFunction(Class<? extends NodeModel> flow) {
        if (!flowHandlerListMap.containsKey(flow)) {
            Info.BlueInfo(String.format("no node regis in node 「%s」, consider as handler only", flow.getName()));
            return;
        }
        List<Function<Container, Container>> functions = flowHandlerListMap.get(flow).stream()
                .filter(item -> !item.getOrder().equals(Float.MAX_VALUE))
                .map(Node::getConductFunction)
                .collect(Collectors.toList());
        Function<Container, Container> conductFunction = (container) -> {
            ContainerUtil.norm(container);
            for (Function<Container, Container> func : functions) {
                container = func.apply(container);
            }
            return container;
        };
        flowConductFunctionMap.put(flow, conductFunction);
    }

    static Function<Container, Container> getFlowConductFunction(Class<? extends NodeModel> modelCla) {
        return flowConductFunctionMap.get(modelCla);
    }

    static Function<Container, Container> getHandlerConductFunction(Class<? extends NodeModel> modelCla) {
        return handlerConductFunctionMap.get(modelCla);
    }

    static List<Node> getAllNodes() {
        return flowHandlerListMap.values().stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getOrder() != Float.MAX_VALUE)
                .collect(Collectors.toList());
    }

}
