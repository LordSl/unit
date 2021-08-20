package com.lordsl.unit.common;

import com.lordsl.unit.common.node.Node;

import java.util.List;

/**
 * 外观类，提供操作接口
 */
public class OpFacade {

    /**
     * HandlerModel的初始化需要HandlerModel的实例，否则无法获取spring容器中的bean
     * 建议是使用Awareness，根据这里给的Class列表，获取到相应实例，再执行初始化
     */
    public static List<Class<? extends NodeModel>> getAllNodeImpl(String pkgName) {
        return AwareUtil.getAllHandlerImpl(pkgName);
    }

    /**
     * 返回一个已存在的Node
     * 若在多个flow中出现，则随机返回一个
     */
    public static Node getNode(Class<? extends NodeModel> target) {
        return AwareUtil.getNode(target);
    }

    public static List<Node> getAllNodes() {
        return Dictator.getAllNodes();
    }

    public static <T> void injectRefer(String name, T bean) {
        Dictator.putRefer(name, bean);
    }

    public static void launchInitTask(List<NodeModel> nodes) {
        AwareUtil.launchInitTasks(nodes);
    }

    public static void outToJson(String path) {
        SchemaResolver.outToJson(path);
    }

    public static void forceConductInitTask(List<NodeModel> nodes) {
        TaskFactory.getFinalDoneTask().run();
    }


}
