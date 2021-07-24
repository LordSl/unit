package com.lordsl.unit.example;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.anno.Produce;
import com.lordsl.unit.common.anno.Unit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Unit(order = {"1.0", "0.9"}, flow = {FlowAlpha.class, FlowBeta.class})
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
