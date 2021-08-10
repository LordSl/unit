package com.lordsl.unit.common;

import com.lordsl.unit.common.anno.*;

import java.util.Map;
import java.util.function.Function;

public class Node {
    private Float order;
    private Class<?> cla;
    private Function<Container, Container> function;
    private Map<String, Class<?>> produces;
    private Map<String, Class<?>> consumes;
    private Map<String, Class<?>> throughs;
    private Map<String, Class<?>> updates;
    private Map<String, Class<?>> refers;

    Node() {
    }

    Node(Float order, Class<?> handler, Function<Container, Container> function) {
        this.order = order;
        this.cla = handler;
        this.function = function;
        produces = PublicFunc.convertMap.apply(PublicFunc.getAnnoFields.apply(Produce.class, cla));
        consumes = PublicFunc.convertMap.apply(PublicFunc.getAnnoFields.apply(Consume.class, cla));
        throughs = PublicFunc.convertMap.apply(PublicFunc.getAnnoFields.apply(Through.class, cla));
        updates = PublicFunc.convertMap.apply(PublicFunc.getAnnoFields.apply(Update.class, cla));
        refers = PublicFunc.convertMap.apply(PublicFunc.getAnnoFields.apply(Refer.class, cla));
    }

    Float getOrder() {
        return order;
    }

    void setOrder(Float order) {
        this.order = order;
    }

    Class<?> getCla() {
        return cla;
    }

    Function<Container, Container> getFunction() {
        return function;
    }

    Map<String, Class<?>> getProduces() {
        return produces;
    }

    Map<String, Class<?>> getConsumes() {
        return consumes;
    }

    Map<String, Class<?>> getThroughs() {
        return throughs;
    }

    public Map<String, Class<?>> getUpdates() {
        return updates;
    }

    Map<String, Class<?>> getRefers() {
        return refers;
    }
}
