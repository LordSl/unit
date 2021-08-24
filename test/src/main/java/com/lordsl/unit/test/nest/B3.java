package com.lordsl.unit.test.nest;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.anno.Consume;
import com.lordsl.unit.common.anno.Handle;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import com.lordsl.unit.common.util.Info;
import org.springframework.stereotype.Component;

@Component
@Unit(unis = {
        @Uni(order = "3.0", flow = A1.class)
})
public class B3 implements NodeModel {
    @Consume
    String Poland;
    @Consume
    String France;
    @Consume
    String Russia;

    @Handle
    public void execute() {
        Info.YellowText(Poland);
        Info.YellowText(France);
        Info.YellowText(Russia);
    }
}
