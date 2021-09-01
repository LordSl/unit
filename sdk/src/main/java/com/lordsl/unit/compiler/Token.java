package com.lordsl.unit.compiler;

public enum Token {
    ;

    public static final String Condition = "con";
    public static final String Logic = "lgc";
    public static final String Compare = "cmp";
    public static final String Cal = "cal";
    public static final String Num = "num";
    public static final String Var = "var";
    public static final String LP = "lp";
    public static final String RP = "rp";
    public static final String Statement = "stat";
    public static final String Init = "ini";

    public static class Regex {
        public static final String Logic = "\\&|\\||\\!";
        public static final String Compare = "\\<|\\=|\\>";
        public static final String Cal = "\\+|\\-|\\*|\\/";
        public static final String Num = "-?\\d+(\\.\\d+)?";
        public static final String Var = "[a-zA-Z]\\w*";
        public static final String LP = "\\(";
        public static final String RP = "\\)";
        public static final String Init = "\\:\\=";
    }
}
