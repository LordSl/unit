package com.lordsl.unit.test.nest;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.util.Container;
import com.lordsl.unit.common.util.Info;
import org.springframework.stereotype.Component;

@Component
public class A1 implements NodeModel {

    public void execute() {
        Container container = new Container();
        container = Stand.execAsFlow(container, this);
        Info.YellowText(String.valueOf(null == container));
    }

}
