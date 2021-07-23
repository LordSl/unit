package org.com.lordsl.unit.common;

public class Signal {
    private static int value = 1;

    public static boolean isPrepare() {
        return value == 1;
    }

    public static boolean isRuntime() {
        return value == 0;
    }

    public static void setPrepare() {
        value = 0;
    }

    public static void setRuntime() {
        value = 0;
    }


}
