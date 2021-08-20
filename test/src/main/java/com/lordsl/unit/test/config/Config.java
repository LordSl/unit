package com.lordsl.unit.test.config;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.OpFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Config {
    @Bean
    void bind() {
        NodeModel[] flows = new NodeModel[]{new FlowTheta()};
        NodeModel[] handlers = new NodeModel[]{new HandlerC1(), new HandlerC2()};
        OpFacade.launchInitTask(Arrays.asList(flows));
        OpFacade.launchInitTask(Arrays.asList(handlers));
    }
}
