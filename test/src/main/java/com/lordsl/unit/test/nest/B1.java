package com.lordsl.unit.test.nest;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.anno.Handle;
import com.lordsl.unit.common.anno.Produce;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import org.springframework.stereotype.Component;

@Component
@Unit(unis = {
        @Uni(order = "1.0", flow = A1.class)
})
public class B1 implements NodeModel {
    @Produce
    String Poland;
    @Produce
    String France;
    @Produce
    String Russia;

    @Handle
    public void execute() {
        Poland = "poland";
        France = "france";
        Russia = "russia";
    }
}
