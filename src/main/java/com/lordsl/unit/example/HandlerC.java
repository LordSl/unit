package com.lordsl.unit.example;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.anno.Consume;
import com.lordsl.unit.common.anno.Through;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Unit(unis = {
        @Uni(order = "2.3", flow = FlowAlpha.class),
        @Uni(order = "2.3", flow = FlowBeta.class)
})
public class HandlerC implements HandlerModel {
    @Consume
    String name;

    @Through
    Integer age;

    @Through
    List<String> courses;

    public HandlerC() {
        Stand.init(this);
    }

    @Override
    public void handle() {
        age += 1;
        courses.add("python");
    }
}
