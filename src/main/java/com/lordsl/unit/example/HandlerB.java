package com.lordsl.unit.example;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.anno.Refer;
import com.lordsl.unit.common.anno.Through;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Unit(unis = {
        @Uni(order = "1.7", flow = FlowAlpha.class),
        @Uni(order = "1.7", flow = FlowBeta.class)
})
public class HandlerB implements HandlerModel {

    @Through
    String name;

    @Through
    Integer age;

    @Refer
    @Autowired
    Hello hello;

    @Through
    List<String> courses;

    public HandlerB() {
        Stand.init(this);
    }

    @Override
    public void handle() {
        hello.hello();
        name += "/B handle";
        age += 1;
        courses.add("c++");
    }
}
