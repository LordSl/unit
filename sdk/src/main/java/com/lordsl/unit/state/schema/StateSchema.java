package com.lordsl.unit.state.schema;

import java.util.List;

public class StateSchema {
    private String id;
    private String desc;
    private List<String> execList;
    private List<RouteSchema> routeList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getExecList() {
        return execList;
    }

    public void setExecList(List<String> execList) {
        this.execList = execList;
    }

    public List<RouteSchema> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<RouteSchema> routeList) {
        this.routeList = routeList;
    }
}
