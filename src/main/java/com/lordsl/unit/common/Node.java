package com.lordsl.unit.common;

import java.util.Map;
import java.util.function.Function;

public class Node {
    private Float order;
    private Class<?> cla;
    private HandlerModel model;
    private Function<Container, Container> function;
    private Map<String, Class<?>> produces;
    private Map<String, Class<?>> consumes;
    private Map<String, Class<?>> throughs;
    private Map<String, Class<?>> refers;

    Node() {
    }

    Node(Float order, HandlerModel model, Function<Container, Container> function) {
        this.order = order;
        this.model = model;
        this.cla = model.getClass();
        this.function = function;
        produces = PublicFunc.convertMap.apply(PublicFunc.getProducesFields.apply(cla));
        consumes = PublicFunc.convertMap.apply(PublicFunc.getConsumesFields.apply(cla));
        throughs = PublicFunc.convertMap.apply(PublicFunc.getThroughsFields.apply(cla));
        refers = PublicFunc.convertMap.apply(PublicFunc.getRefersFields.apply(cla));
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

    HandlerModel getModel() {
        return model;
    }

    Map<String, Class<?>> getRefers() {
        return refers;
    }
}
