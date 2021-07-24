package org.com.lordsl.unit.alpha;

import org.com.lordsl.unit.common.HandlerModel;
import org.com.lordsl.unit.common.anno.Produce;
import org.com.lordsl.unit.common.anno.Unit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Unit(order = "1.0", flow = FlowAlpha.class)
public class HandlerA implements HandlerModel {

    @Produce
    String name;

    @Produce
    Integer age;

    @Produce
    List<String> courses;

    public HandlerA() {
        init();
    }

    @Override
    public void handle() {
        name = "/A handle";
        age = 17;
        courses = new ArrayList<>();
        courses.add("java");
    }
}
