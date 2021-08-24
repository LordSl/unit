package com.lordsl.unit.test.config;

import com.lordsl.unit.common.NodeModel;
import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.common.schema.NodeSchema;
import com.lordsl.unit.test.example.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class FlowThetaConfig {
    String filePath = getRootPath() + "/schema.json";

    String getRootPath() {
        try {
            return new File("").getCanonicalPath();
        } catch (Exception e) {
            return null;
        }
    }

    @Bean
    void initFlowTheta() {
        List<NodeModel> nodeModelList = new ArrayList<>();
        nodeModelList.add(BeanFetcher.getBeanOfClass(HandlerC1.class));
        nodeModelList.add(BeanFetcher.getBeanOfClass(HandlerC2.class));
        nodeModelList.add(BeanFetcher.getBeanOfClass(FlowTheta.class));
        nodeModelList.add(BeanFetcher.getBeanOfClass(FlowAlpha.class));
        nodeModelList.add(BeanFetcher.getBeanOfClass(FlowBeta.class));
        nodeModelList.add(BeanFetcher.getBeanOfClass(HandlerA.class));
        nodeModelList.add(BeanFetcher.getBeanOfClass(HandlerB.class));
        nodeModelList.add(BeanFetcher.getBeanOfClass(HandlerC.class));
        List<NodeSchema> nodeSchemaList = OpFacade.readNodeSchemaList(filePath);
        OpFacade.launchInitTask(nodeModelList, nodeSchemaList);
    }
}
