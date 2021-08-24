package com.lordsl.unit.test.config;

import com.lordsl.unit.common.NodeModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
//@Unit(unis = @Uni(order = "1.0", flow = FlowTheta.class))
public class HandlerC1 implements NodeModel {
    //    @Produce
    String name;

    //    @Produce
    Integer age;

    //    @Produce
    List<String> courses;

    //    @Handle
    public void handle() {
        name = "/C1 handle";
        age = 17;
        courses = new ArrayList<>();
        courses.add("java");
    }

}
