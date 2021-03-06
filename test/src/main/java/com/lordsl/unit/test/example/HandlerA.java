package com.lordsl.unit.test.example;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.common.anno.Handle;
import com.lordsl.unit.common.anno.Produce;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Component
@Unit(unis = {
        @Uni(order = "1.0", flow = FlowAlpha.class),
        @Uni(order = "0.9", flow = FlowBeta.class),
})
public class HandlerA implements NodeModel {
    @Produce
    String name;

    @Produce
    Integer age;

    @Produce
    List<String> courses;

    public HandlerA(Integer mark) {
    }

    public HandlerA() {
        OpFacade.initAsHandler(this);
    }

    @Override
    public Supplier<NodeModel> getTemplate() {
        return () -> new HandlerA(1);
    }

    @Handle
    public void handle() {
        name = "/A handle";
        age = 17;
        courses = new ArrayList<>();
        courses.add("java");
    }
}
