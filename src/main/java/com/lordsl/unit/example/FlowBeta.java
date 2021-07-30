package com.lordsl.unit.example;

import com.lordsl.unit.common.Container;
import com.lordsl.unit.common.FlowModel;
import org.springframework.stereotype.Component;

@Component
public class FlowBeta implements FlowModel {

    public Container exec() {
        Container container = new Container();
        return Stand.execAsChain(container, this);
    }
}
