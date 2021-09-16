package com.lordsl.unit.state.schema;

import java.util.List;

public class RouterSchema {
    private String id;
    private String desc;
    private List<RouteSchema> routeList;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<RouteSchema> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<RouteSchema> routeList) {
        this.routeList = routeList;
    }

}
