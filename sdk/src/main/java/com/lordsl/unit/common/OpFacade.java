package com.lordsl.unit.common;

import com.lordsl.unit.common.node.Node;
import com.lordsl.unit.common.schema.NodeSchema;
import com.lordsl.unit.common.util.Container;

import java.util.List;
import java.util.function.Function;

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

    /**
     * 获取所有已注册的Node实例
     */
    public static List<Node> getAllNodes() {
        return Dictator.getAllNodes();
    }

    /**
     * 向refer容器中注册一个实例，常用于bean
     */
    public static <T> void injectRefer(String name, T bean) {
        Dictator.putRefer(name, bean);
    }

    /**
     * 使用多个NodeModel实例进行注册，信息来源于注解
     */
    public static void launchInitTask(List<NodeModel> nodes) {
        nodes.forEach(model ->
                TaskFactory.getHandlerInitTask(model).forEach(TaskPool::addHandlerInitTask)
        );
        nodes.forEach(model -> TaskPool.addFlowInitTask(TaskFactory.getFlowInitTask(model)));
    }

    /**
     * 将所有Node的NodeSchema的JSON字符串输出到文件
     */
    public static void outToJson(String path) {
        SchemaResolver.outToJson(path);
    }

    /**
     * 强制执行TaskPool中的所有任务，按顺序是
     * 1.生成并注册所有Node
     * 2.注入Refer依赖
     * 3.聚合生成所有Flow的可执行函数
     */
    public static void forceConductInitTask(List<NodeModel> nodes) {
        TaskFactory.getFinalDoneTask().run();
    }

    /**
     * 读取json文件，得到NodeSchema的列表
     */
    public static List<NodeSchema> readNodeSchemaList(String path) {
        return SchemaResolver.readNodeSchemaList(path);
    }

    /**
     * 使用多个NodeModel实例进行注册，信息来源于提供的schema
     */
    public static void launchInitTask(List<NodeModel> nodes, List<NodeSchema> schemas) {
        TaskFactory.getHandlerInitTask(nodes, schemas).forEach(TaskPool::addHandlerInitTask);
        nodes.forEach(model -> TaskPool.addFlowInitTask(TaskFactory.getFlowInitTask(model)));
    }

    /**
     * 返回NodeModel实例的类所对应的Flow执行函数
     */
    public static Function<Container, Container> getConductFunction(NodeModel model) {
        return Dictator.getConductFunction(model);
    }
}
