package com.lordsl.unit.test.compare;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.anno.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Unit(unis = {
        @Uni(order = "2.0", flow = FlowB.class)
})
public class HandlerB2 implements NodeModel {
    @Consume
    ArrayList<Object> c;
    @Produce
    Long f;

    public HandlerB2() {
        Stand.initAsHandler(this);
    }

    @Handle
    public void handle() {
        f = 0L;
        String d = String.valueOf(c.get(0));
        long e = (long) c.get(0).hashCode() * 101;
        for (Object o : c) {
            long tmp = o.hashCode() * d.hashCode() / (e + 1);
//            Info.BlueInfo(Long.toString(tmp));
            f += tmp;
        }
    }
}
