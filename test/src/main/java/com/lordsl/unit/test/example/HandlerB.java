package com.lordsl.unit.test.example;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.common.anno.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Unit(unis = {
        @Uni(order = "1.7", flow = FlowAlpha.class),
        @Uni(order = "1.7", flow = FlowBeta.class)
})
public class HandlerB implements NodeModel {

    @Update
    String name;

    @Update
    Integer age;

    @Refer
    @Autowired
    Hello hello;

    @Update
    List<String> courses;

    public HandlerB() {
        OpFacade.initAsHandler(this);
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
