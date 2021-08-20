package com.lordsl.unit.test.example;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.anno.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Unit(unis = {
        @Uni(order = "2.3", flow = FlowAlpha.class),
        @Uni(order = "2.3", flow = FlowBeta.class)
})
public class HandlerC implements NodeModel {
    @Consume
    String name;

    @Update
    Integer age;

    @Update
    List<String> courses;

    public HandlerC() {
        Stand.initAsHandler(this);
    }

    @Handle
    public void handle() {
        age += 1;
        courses.add("python");
    }
}
