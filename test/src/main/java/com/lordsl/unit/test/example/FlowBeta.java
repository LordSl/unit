package com.lordsl.unit.test.example;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.common.util.Container;
import org.springframework.stereotype.Component;

@Component
public class FlowBeta implements NodeModel {

    FlowBeta() {
        OpFacade.initAsFlow(this);
    }

    public Container exec() {
        Container container = new Container();
        return OpFacade.execAsFlow(container, this);
    }
}
