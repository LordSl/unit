package com.lordsl.unit.common.util;

public class Info {

    private static void printWithColor(String s, int code) {
        System.out.printf("\u001B[%dm%s\u001B[0m%n", code, s);
    }

    public static void YellowText(String s) {
        printWithColor(String.format("[TEXT] %s", s), 33);
    }

    public static void BlueInfo(String s) {
        printWithColor(String.format("[INFO] %s", s), 34);
    }

    public static void PurpleAlert(String s) {
        printWithColor(String.format("[ALERT] %s", s), 35);
    }

    public static void GreenLog(String s) {
        printWithColor(String.format("[LOG] %s", s), 36);
    }

}
