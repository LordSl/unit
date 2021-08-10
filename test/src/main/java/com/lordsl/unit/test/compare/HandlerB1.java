package com.lordsl.unit.test.compare;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.anno.*;
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

    @Handle
    public void handle() {
        c = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            c.add(a * b.hashCode() % (i + 1));
        }
    }
}
