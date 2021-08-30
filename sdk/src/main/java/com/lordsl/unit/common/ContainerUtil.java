package com.lordsl.unit.common;

import com.lordsl.unit.util.Container;

public class ContainerUtil {

    /**
     * 创建一个规范的带有自指针和条件区的容器
     */
    public static Container create() {
        Container container = new Container();
        container.put(Const.SelfRefer, container);
        container.put(Const.ConditionZone, new Container());
        return container;
    }

    /**
     * 给一个Container加上自指针和条件区
     */
    public static Container norm(Container container) {
        if (null == container.get(Const.SelfRefer))
            container.put(Const.SelfRefer, container);
        if (null == container.get(Const.ConditionZone))
            container.put(Const.ConditionZone, new Container());
        return container;
    }

    /**
     * 创建一个子容器，条件区继承自父容器
     */
    public static Container subOf(Container parent) {
        Container child = new Container();
        child.put(Const.SelfRefer, child);
        if (null == parent.get(Const.ConditionZone))
            child.put(Const.ConditionZone, new Container());
        else
            child.put(Const.ConditionZone, parent.get(Const.ConditionZone));
        return child;
    }
}
