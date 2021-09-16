package com.lordsl.unit.state.schema;

public class RouteSchema {
    private String toState;
    private String condition;

    public String getToState() {
        return toState;
    }


    public void setToState(String toState) {
        this.toState = toState;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}
