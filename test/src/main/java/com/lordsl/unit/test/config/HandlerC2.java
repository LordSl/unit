package com.lordsl.unit.test.config;

import com.lordsl.unit.common.NodeModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@Unit(unis = @Uni(order = "2.0", flow = FlowTheta.class))
public class HandlerC2 implements NodeModel {
    //    @Update
    String name;

    //    @Update
    Integer age;

    //    @Update
    List<String> courses;

    //    @Handle
    public void handle() {
        name += "/C2 handle";
        age += 1;
        courses.add("c++");
    }

}
