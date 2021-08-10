package com.lordsl.unit.common;

public class Signal {
    //在FlowModel类的执行方法第一次被调用时，关闭Handler的注册开关，结束构造

    private static int value = 1;

    static boolean isOn() {
        return value == 1;
    }

    static boolean isOff() {
        return value == 0;
    }

    static void setOn() {
        value = 0;
    }

    static void setOff() {
        value = 0;
    }


}
