package com.lordsl.unit.test.compare;

import com.lordsl.unit.common.Container;
import com.lordsl.unit.common.FlowModel;
import com.lordsl.unit.common.OpFacade;
import org.springframework.stereotype.Component;

@Component
public class FlowB implements FlowModel {

    FlowB() {
        Stand.init(this);
    }

    public void exec() {
        Container container = new Container();
        container.put("a", 103);
        container.put("b", "iwhrinwlakdnlkjoi2803412740hsna");
        container = Stand.execAsChain(container, this);
        OpFacade.PurpleAlert("res->" + container.get("f").toString());
    }
}
