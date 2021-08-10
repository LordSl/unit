package com.lordsl.unit.common;

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
    public static List<Class<? extends HandlerModel>> getAllHandlerImpl(String pkgName) {
        return AwareUtil.getAllHandlerImpl(pkgName);
    }

    /**
     * FlowModel的初始化不依赖于实例，但仍建议采用默认的懒加载方式，以免在HandlerModel注册之前注册
     */
    public static List<Class<? extends FlowModel>> getAllFlowImpl(String pkgName) {
        return AwareUtil.getAllFlowImpl(pkgName);
    }

    /**
     * 使用无参构造器强制初始化实例
     */
    public static void forceInitHandlers(List<Class<? extends HandlerModel>> list) {
        AwareUtil.forceInitHandlers(list);
    }

    /**
     * 使用无参构造器强制初始化实例
     */
    public static void forceInitFlows(List<Class<? extends FlowModel>> list) {
        AwareUtil.forceInitFlows(list);
    }

    /**
     * 返回一个已存在的Node
     * 若在多个flow中出现，则随机返回一个
     */
    public static Node getNode(Class<? extends HandlerModel> target) {
        return AwareUtil.getNode(target);
    }

    public static Function<Container, Container> getFunc(Class<? extends HandlerModel> target) {
        return AwareUtil.getFunc(target);
    }

    public static void outToJson(String path) {
        Info.outToJson(path);
    }

    public static void outToJson() {
        Info.outToJson();
    }

    public static void YellowText(String s) {
        Info.YellowText(s);
    }

    public static void BlueInfo(String s) {
        Info.BlueInfo(s);
    }

    public static void PurpleAlert(String s) {
        Info.PurpleAlert(s);
    }

    public static void GreenLog(String s) {
        Info.GreenLog(s);
    }

    public static <T> void injectRefer(String name, T bean) {
        Dictator.putRefer(name, bean);
    }

}
