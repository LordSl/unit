package com.lordsl.unit.test.nest;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.util.Container;
import com.lordsl.unit.util.Info;
import org.springframework.stereotype.Component;

@Component
public class A1 implements NodeModel {

    public void execute() {
        Container container = new Container();
        container = OpFacade.execAsFlow(container, this);
        Info.YellowText(String.valueOf(null == container));
    }

}
