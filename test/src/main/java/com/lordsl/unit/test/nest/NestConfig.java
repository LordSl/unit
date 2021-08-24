package com.lordsl.unit.test.nest;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.test.config.BeanFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class NestConfig {
    @Bean
    void init() {
        List<Class<? extends NodeModel>> list = OpFacade.getAllNodeImpl("com.lordsl.unit.test.nest");
        List<NodeModel> nodeModelList = new ArrayList<>();
        list.forEach(
                cla -> nodeModelList.add(BeanFetcher.getBeanOfClass(cla))
        );
        OpFacade.launchInitTask(nodeModelList);
    }
}
