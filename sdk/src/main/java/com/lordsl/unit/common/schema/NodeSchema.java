package com.lordsl.unit.common.schema;

import java.util.List;

public class NodeSchema {
    String fullClass;
    String simpleClass;
    String order;
    String flow;
    List<KeySchema> produceList;
    List<KeySchema> throughList;
    List<KeySchema> updateList;
    List<KeySchema> consumeList;
    List<KeySchema> referList;
    List<MethodSchema> methodList;

    public String getFullClass() {
        return fullClass;
    }

    public void setFullClass(String fullClass) {
        this.fullClass = fullClass;
    }

    public String getSimpleClass() {
        return simpleClass;
    }

    public void setSimpleClass(String simpleClass) {
        this.simpleClass = simpleClass;
    }

    public List<KeySchema> getProduceList() {
        return produceList;
    }

    public void setProduceList(List<KeySchema> produceList) {
        this.produceList = produceList;
    }

    public List<KeySchema> getThroughList() {
        return throughList;
    }

    public void setThroughList(List<KeySchema> throughList) {
        this.throughList = throughList;
    }

    public List<KeySchema> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<KeySchema> updateList) {
        this.updateList = updateList;
    }

    public List<KeySchema> getConsumeList() {
        return consumeList;
    }

    public void setConsumeList(List<KeySchema> consumeList) {
        this.consumeList = consumeList;
    }

    public List<KeySchema> getReferList() {
        return referList;
    }

    public void setReferList(List<KeySchema> referList) {
        this.referList = referList;
    }

    public List<MethodSchema> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<MethodSchema> methodList) {
        this.methodList = methodList;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }
}
