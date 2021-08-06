package com.lordsl.unit.example;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.anno.*;
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

    @Handle
    public void handleCommon() {
        hello.hello();
        name += "/B handle";
        age += 1;
        courses.add("c++");
    }

    @Handle(pos = "2", flows = {FlowAlpha.class})
    public void handleAlpha() {
        System.out.println("对阿尔法的悄悄话");
    }

    @Handle(pos = "2", flows = {FlowBeta.class})
    public void handleBeta() {
        System.out.println("对贝塔的悄悄话");

    }
}
