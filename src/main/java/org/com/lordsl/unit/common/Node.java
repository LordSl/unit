package org.com.lordsl.unit.common;

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

    public Float getOrder() {
        return order;
    }

    public void setOrder(Float order) {
        this.order = order;
    }

    public Class<?> getCla() {
        return cla;
    }

    public Function<Container, Container> getFunction() {
        return function;
    }

    public Map<String, Class<?>> getProduces() {
        return produces;
    }

    public Map<String, Class<?>> getConsumes() {
        return consumes;
    }

    public Map<String, Class<?>> getThroughs() {
        return throughs;
    }

    public HandlerModel getModel() {
        return model;
    }

    public Map<String, Class<?>> getRefers() {
        return refers;
    }
}
