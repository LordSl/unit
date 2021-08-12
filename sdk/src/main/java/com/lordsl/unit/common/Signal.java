package com.lordsl.unit.common;

public class Signal {
    //在FlowModel类的执行方法第一次被调用时，关闭Handler的注册开关，结束构造

    private static boolean regisEnable = true;
    private static boolean interactionCheckEnable = true;

    static boolean regisEnable() {
        return regisEnable;
    }

    static void regisEnable(boolean b) {
        regisEnable = b;
    }

    static boolean interactionCheckEnable() {
        return interactionCheckEnable;
    }

    static void interactionCheckEnable(boolean b) {
        interactionCheckEnable = b;
    }


}
