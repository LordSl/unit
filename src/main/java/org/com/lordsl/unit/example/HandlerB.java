package org.com.lordsl.unit.example;

import org.com.lordsl.unit.common.HandlerModel;
import org.com.lordsl.unit.common.anno.Refer;
import org.com.lordsl.unit.common.anno.Through;
import org.com.lordsl.unit.common.anno.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Unit(order = {"1.7", "1.7"}, flow = {FlowAlpha.class, FlowBeta.class})
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
        init();
    }

    @Override
    public void handle() {
        hello.hello();
        name += "/B handle";
        age += 1;
        courses.add("c++");
    }
}
