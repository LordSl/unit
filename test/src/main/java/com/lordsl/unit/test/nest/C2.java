package com.lordsl.unit.test.nest;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.anno.Handle;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import com.lordsl.unit.common.anno.Update;
import org.springframework.stereotype.Component;

@Component
@Unit(unis = {
        @Uni(order = "2.0", flow = B2.class)
})
public class C2 implements NodeModel {
    @Update
    String country;

    @Handle
    public void execute() {
        country = country + "and then oh my god.../";
    }
}
