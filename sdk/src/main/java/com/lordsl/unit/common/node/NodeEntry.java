package com.lordsl.unit.common.node;

public class NodeEntry<T> {
    private String key;
    private T val;

    NodeEntry(String key, T val) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

}
