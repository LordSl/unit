package com.lordsl.unit.test.nest;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.common.anno.Handle;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import com.lordsl.unit.common.anno.Update;
import com.lordsl.unit.util.Container;
import org.springframework.stereotype.Component;

@Component
@Unit(unis = {
        @Uni(order = "2.0", flow = A1.class)
})
public class B2 implements NodeModel {
    @Update
    String Poland;
    @Update
    String France;
    @Update
    String Russia;

    @Handle
    public void execute() {
        Poland = handleWrap(Poland);
        France = handleWrap(France);
        Russia = handleWrap(Russia);
    }

    public String handleWrap(String country) {
        Container container = new Container();
        container.put("country", country);
        return OpFacade.execAsFlow(container, this).get("country");
    }
}
