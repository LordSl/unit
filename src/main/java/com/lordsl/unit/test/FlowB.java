package com.lordsl.unit.test;

import com.lordsl.unit.common.Container;
import com.lordsl.unit.common.FlowModel;
import com.lordsl.unit.common.Info;
import org.springframework.stereotype.Component;

@Component
public class FlowB implements FlowModel {
    public void exec() {
        Container container = new Container();
        container.put("a", 103);
        container.put("b", "iwhrinwlakdnlkjoi2803412740hsna");
        container = Stand.execAsChain(container, this);
        Info.PurpleAlert("res->" + container.get("f").toString());
    }
}
