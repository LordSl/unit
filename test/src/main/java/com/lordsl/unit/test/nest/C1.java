package com.lordsl.unit.test.nest;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.anno.Handle;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;
import com.lordsl.unit.common.anno.Update;
import org.springframework.stereotype.Component;

@Component
@Unit(unis = {
        @Uni(order = "1.0", flow = B2.class)
})
public class C1 implements NodeModel {
    @Update
    String country;

    @Handle
    public void execute() {
        country = "first, this is a country named " + country + "/";
    }
}
