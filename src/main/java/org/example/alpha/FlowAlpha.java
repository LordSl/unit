package org.example.alpha;

import org.example.util.AbstractFlow;
import org.example.util.Container;
import org.springframework.stereotype.Component;

@Component
public class FlowAlpha extends AbstractFlow {

    public Container exec() {
        //todo 书写业务代码，将（可能的）入参装入容器
        Container container = new Container();
        container = execAsChain(container);
        //todo 书写业务代码，从容器到出参
        return container;
    }

}
