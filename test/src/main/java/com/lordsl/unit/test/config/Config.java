package com.lordsl.unit.test.config;

import com.lordsl.unit.common.FlowModel;
import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.OpFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Config {
    @Bean
    void bind() {
        FlowModel[] flows = new FlowModel[]{new FlowTheta()};
        HandlerModel[] handlers = new HandlerModel[]{new HandlerC1(), new HandlerC2()};
        OpFacade.softInitHandlers(Arrays.asList(handlers));
        OpFacade.softInitFlows(Arrays.asList(flows));
    }
}
