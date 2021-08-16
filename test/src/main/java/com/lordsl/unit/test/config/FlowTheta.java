package com.lordsl.unit.test.config;

import com.lordsl.unit.common.Container;
import com.lordsl.unit.common.FlowModel;

public class FlowTheta implements FlowModel {

    public Container exec() {
        Container container = new Container();
        return Stand.execAsChain(container, this);
    }

}
