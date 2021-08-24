package com.lordsl.unit.common;

public class Signal {
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
