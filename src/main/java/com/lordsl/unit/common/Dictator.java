package com.lordsl.unit.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Dictator {
    static final Map<Class<?>, List<Node>> flowNodesMap = new HashMap<>();

    private final static Container refers = new Container();

    static <T> void putRefer(String name, T t) {
        refers.put(name, t);
    }

    static <T> T getRefer(String name) {
        return refers.get(name);
    }

    static void put(Class<?> flow, Node newNode) {
        List<Node> nodes;

        if (!flowNodesMap.containsKey(flow)) {
            nodes = new LinkedList<>();
            Node barrier = new Node();
            barrier.setOrder(Float.MAX_VALUE);
            nodes.add(barrier);
            flowNodesMap.put(flow, nodes);
        } else
            nodes = flowNodesMap.get(flow);

        int index = 0;
        for (; index < nodes.size(); index++) {
            Node thisNode = nodes.get(index);
            if (thisNode.getOrder().equals(newNode.getOrder()))
                return;
            if (thisNode.getOrder() > newNode.getOrder())
                break;
        }
        float tmp = index > 0 ? newNode.getOrder() - nodes.get(index - 1).getOrder() : 0;
        if (0 < tmp && tmp <= 0.1)
            nodes.set(index - 1, newNode);
        else
            nodes.add(index, newNode);
    }

    static List<Node> get(Class<?> flow) {
        List<Node> nodes = flowNodesMap.get(flow);
        return nodes.subList(0, nodes.size() - 1);
    }

}
