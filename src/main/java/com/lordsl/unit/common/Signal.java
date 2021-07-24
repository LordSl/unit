package com.lordsl.unit.common;

public class Signal {
    private static int value = 1;

    public static boolean isOn() {
        return value == 1;
    }

    public static boolean isOff() {
        return value == 0;
    }

    public static void setOn() {
        value = 0;
    }

    public static void setOff() {
        value = 0;
    }


}
