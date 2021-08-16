package com.lordsl.unit.test.example;

import com.lordsl.unit.common.Container;
import com.lordsl.unit.common.FlowModel;
import org.springframework.stereotype.Component;

@Component
public class FlowAlpha implements FlowModel {

    FlowAlpha() {
        Stand.init(this);
    }

    public Container exec() {
        Container container = new Container();
        return Stand.execAsChain(container, this);
    }

}
