package com.lordsl.unit.test;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.anno.Consume;
import com.lordsl.unit.common.anno.Produce;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Unit(unis = {
        @Uni(order = "1.0", flow = FlowB.class)
})
public class HandlerB1 implements HandlerModel {
    @Consume
    Integer a;
    @Consume
    String b;
    @Produce
    ArrayList<Object> c;

    public HandlerB1() {
        Stand.init(this);
    }

    @Override
    public void handle() {
        c = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            c.add(a * b.hashCode() % (i + 1));
        }
    }
}
