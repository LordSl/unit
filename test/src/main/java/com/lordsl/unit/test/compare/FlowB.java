package com.lordsl.unit.test.compare;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.common.util.Container;
import com.lordsl.unit.common.util.Info;
import org.springframework.stereotype.Component;

@Component
public class FlowB implements NodeModel {

    FlowB() {
        OpFacade.initAsFlow(this);
    }

    public void exec() {
        Container container = new Container();
        container.put("a", 103);
        container.put("b", "iwhrinwlakdnlkjoi2803412740hsna");
        container = OpFacade.execAsFlow(container, this);
        Info.PurpleAlert("res->" + container.get("f").toString());
    }
}
