package org.com.lordsl.unit.common;

import java.util.Map;
import java.util.function.Function;

public class Node {
    private Float order;
    private Class<?> cla;
    private Function<Container, Container> function;
    private Map<String, Class<?>> produces;
    private Map<String, Class<?>> consumes;
    private Map<String, Class<?>> throughs;

    Node() {
    }

    Node(Float order, Class<?> cla, Function<Container, Container> function) {
        this.order = order;
        this.cla = cla;
        this.function = function;
        produces = PublicFunc.convertMap.apply(PublicFunc.getProducesFields.apply(cla));
        consumes = PublicFunc.convertMap.apply(PublicFunc.getConsumesFields.apply(cla));
        throughs = PublicFunc.convertMap.apply(PublicFunc.getThroughsFields.apply(cla));
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

    public void setCla(Class<?> cla) {
        this.cla = cla;
    }

    public Function<Container, Container> getFunction() {
        return function;
    }

    public void setFunction(Function<Container, Container> function) {
        this.function = function;
    }

    public Map<String, Class<?>> getProduces() {
        return produces;
    }

    public void setProduces(Map<String, Class<?>> produces) {
        this.produces = produces;
    }

    public Map<String, Class<?>> getConsumes() {
        return consumes;
    }

    public void setConsumes(Map<String, Class<?>> consumes) {
        this.consumes = consumes;
    }

    public Map<String, Class<?>> getThroughs() {
        return throughs;
    }

    public void setThroughs(Map<String, Class<?>> throughs) {
        this.throughs = throughs;
    }
}
