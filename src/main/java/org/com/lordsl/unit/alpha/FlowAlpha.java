package org.com.lordsl.unit.alpha;

import org.com.lordsl.unit.common.Container;
import org.com.lordsl.unit.common.AbstractFlow;
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

    @Override
    protected void setParams(Container container) {
        //todo 填写参数（需要用到的java bean，请使用setParam(...)标签方法来设置）
    }
}
