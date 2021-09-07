package com.lordsl.unit.util;

public class Info {

    private static void printWithColor(String s, int code) {
        System.out.printf("\u001B[%dm%s\u001B[0m%n", code, s);
    }

    public static void YellowText(Object o) {
        printWithColor(String.format("[TEXT] %s", o.toString()), 33);
    }

    public static void BlueInfo(Object o) {
        printWithColor(String.format("[INFO] %s", o.toString()), 34);
    }

    public static void PurpleAlert(Object o) {
        printWithColor(String.format("[ALERT] %s", o.toString()), 35);
    }

    public static void GreenLog(Object o) {
        printWithColor(String.format("[LOG] %s", o.toString()), 36);
    }

    public static void WhiteLine() {
        System.out.println();
    }

}
