package com.lordsl.unit.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Dictator {
    private static final Map<Class<? extends FlowModel>, List<Node>> flowNodesMap = new HashMap<>();

    private final static Container refers = new Container();

    static <T> void putRefer(String name, T t) {
        refers.put(name, t);
    }

    static <T> T getRefer(String name) {
        return refers.get(name);
    }

    static void putNode(Class<? extends FlowModel> flow, Node newNode) {
        List<Node> nodes;

        if (!flowNodesMap.containsKey(flow)) {
            nodes = new LinkedList<>();
            Node barrier = new Node(Float.MIN_VALUE);
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

    static List<Node> getNodes(Class<?> flow) {
        List<Node> nodes = flowNodesMap.get(flow);
        return nodes.subList(0, nodes.size() - 1);
    }

    static Map<Class<? extends FlowModel>, List<Node>> getFlowNodesMap() {
        return flowNodesMap;
    }
}
