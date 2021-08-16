package com.lordsl.unit.test.config;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.anno.Handle;
import com.lordsl.unit.common.anno.Produce;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;

import java.util.ArrayList;
import java.util.List;

@Unit(unis = @Uni(order = "1.0", flow = FlowTheta.class))
public class HandlerC1 implements HandlerModel {
    @Produce
    String name;

    @Produce
    Integer age;

    @Produce
    List<String> courses;

    @Handle
    public void handle() {
        name = "/C1 handle";
        age = 17;
        courses = new ArrayList<>();
        courses.add("java");
    }

}
