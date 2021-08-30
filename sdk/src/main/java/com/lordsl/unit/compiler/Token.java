package com.lordsl.unit.compiler;

import com.lordsl.unit.util.Info;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {
    ;

    public static void main(String[] args) {
        test(Regex.Var, "a3");
        test(Regex.Var, "qwe_8");
        test(Regex.Num, "-3.230");
        test(Regex.Num, "-298");
        test(Regex.Num, "0");
        test(Regex.Num, "-2.asd3");
        test(Regex.Cal, "+");
        test(Regex.Cal, "#");
        test(Regex.Compare, ">");
        test(Regex.Compare, ">>");
        test(Regex.Logic, "&");
        test(Regex.Logic, "@");
        test(Regex.LP, "(");
        test(Regex.RP, ")");
    }

    public static void test(String regex, String test) {
        Info.GreenLog(String.format("for regex 「%s」 input 「%s」", regex, test));
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(test);
        boolean isMatch = m.matches();
        Info.PurpleAlert((String.format("match or not: %s", isMatch)));
        if (isMatch)
            Info.YellowText(String.format("match res: %s", m.group(0)));
        Info.WhiteLine();
    }

    public static final String Condition = "con";
    public static final String Logic = "lgc";
    public static final String Compare = "cmp";
    public static final String Cal = "cal";
    public static final String Num = "num";
    public static final String Var = "var";
    public static final String LP = "lp";
    public static final String RP = "rp";

    public static class Regex {
        public static final String Logic = "\\&|\\||\\!";
        public static final String Compare = "\\<|\\=|\\>";
        public static final String Cal = "\\+|\\-|\\*|\\/";
        public static final String Num = "-?\\d+(\\.\\d+)?";
        public static final String Var = "[a-zA-Z]\\w*";
        public static final String LP = "\\(";
        public static final String RP = "\\)";
    }
}
