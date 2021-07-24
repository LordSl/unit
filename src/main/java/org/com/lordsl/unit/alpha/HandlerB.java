package org.com.lordsl.unit.alpha;

import org.com.lordsl.unit.common.HandlerModel;
import org.com.lordsl.unit.common.anno.Through;
import org.com.lordsl.unit.common.anno.Unit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Unit(order = "1.7", flow = FlowAlpha.class)
public class HandlerB implements HandlerModel {

    @Through
    String name;

    @Through
    Integer age;

    @Through
    List<String> courses;

    public HandlerB() {
        init();
    }

    @Override
    public void handle() {
        name += "/B handle";
        age += 1;
        courses.add("c++");
    }
}
