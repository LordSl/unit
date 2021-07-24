package com.lordsl.unit.test;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.anno.Consume;
import com.lordsl.unit.common.anno.Produce;
import com.lordsl.unit.common.anno.Unit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Unit(order = "2.0", flow = FlowB.class)
public class HandlerB2 implements HandlerModel {
    @Consume
    ArrayList<Object> c;
    @Produce
    Long f;

    public HandlerB2() {
        init();
    }

    @Override
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
