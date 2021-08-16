package com.lordsl.unit.common;

import java.util.List;

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
     * 传入一个同级class作为参数，同包即可，没有其它要求
     */
    public static List<Class<? extends HandlerModel>> getAllHandlerImpl(Class<?> peer) {
        return AwareUtil.getAllHandlerImpl(peer);
    }

    /**
     * FlowModel的初始化不依赖于实例，但仍建议采用默认的懒加载方式，以免在HandlerModel注册之前注册
     */
    public static List<Class<? extends FlowModel>> getAllFlowImpl(String pkgName) {
        return AwareUtil.getAllFlowImpl(pkgName);
    }

    /**
     * 传入一个同级class作为参数，同包即可，没有其它要求
     */
    public static List<Class<? extends FlowModel>> getAllFlowImpl(Class<?> peer) {
        return AwareUtil.getAllFlowImpl(peer);
    }

    /**
     * 返回一个已存在的Node
     * 若在多个flow中出现，则随机返回一个
     */
    public static Node getNode(Class<? extends HandlerModel> target) {
        return AwareUtil.getNode(target);
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

    public static void softInitHandlers(List<HandlerModel> handlers) {
        AwareUtil.softInitHandlers(handlers);
    }

    public static void softInitFlows(List<FlowModel> flows) {
        AwareUtil.softInitFlows(flows);
    }
}
