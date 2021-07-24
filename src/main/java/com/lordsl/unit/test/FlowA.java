package com.lordsl.unit.test;

import com.lordsl.unit.common.Info;
import org.springframework.stereotype.Component;

@Component
public class FlowA {

    public void exec() {
        HandlerA1 handlerA1 = new HandlerA1();
        HandlerA2 handlerA2 = new HandlerA2();

        HandlerA1.task1Resp resp1 = handlerA1.handle(new HandlerA1.task1Req(103, "iwhrinwlakdnlkjoi2803412740hsna"));
        HandlerA2.task2Resp resp2 = handlerA2.handle(new HandlerA2.task2Req(resp1.c));
        Info.PurpleAlert("res->" + resp2.f.toString());

    }
}
