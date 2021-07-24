package org.com.lordsl.unit.alpha;

import org.com.lordsl.unit.common.HandlerModel;
import org.com.lordsl.unit.common.anno.Consume;
import org.com.lordsl.unit.common.anno.Through;
import org.com.lordsl.unit.common.anno.Unit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Unit(order = "2.3", flow = FlowAlpha.class)
public class HandlerC implements HandlerModel {
    @Consume
    String name;

    @Through
    Integer age;

    @Through
    List<String> courses;

    public HandlerC() {
        init();
    }

    @Override
    public void handle() {
        System.out.println(name);
        age += 1;
        courses.add("python");
    }
}
