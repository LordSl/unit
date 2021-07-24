package com.lordsl.unit.example;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.anno.Consume;
import com.lordsl.unit.common.anno.Through;
import com.lordsl.unit.common.anno.Unit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Unit(order = {"2.3", "1.71"}, flow = {FlowAlpha.class, FlowBeta.class})
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
        age += 1;
        courses.add("python");
    }
}