package org.com.lordsl.unit.example;

import org.com.lordsl.unit.common.Container;
import org.com.lordsl.unit.common.FlowModel;
import org.springframework.stereotype.Component;

@Component
public class FlowAlpha implements FlowModel {

    public Container exec() {
        Container container = new Container();
        return execAsChain(container);
    }

}
