package com.lordsl.unit.test.config;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.anno.Handle;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import com.lordsl.unit.common.anno.Update;

import java.util.List;

@Unit(unis = @Uni(order = "2.0", flow = FlowTheta.class))
public class HandlerC2 implements HandlerModel {
    @Update
    String name;

    @Update
    Integer age;

    @Update
    List<String> courses;

    @Handle
    public void handle() {
        name += "/C2 handle";
        age += 1;
        courses.add("c++");
    }

}
