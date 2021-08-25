package com.lordsl.unit.common.condition.parser.token;

import java.util.function.BiFunction;

public class CalToken {
    private BiFunction<Number, Number, Number> cal = null;

    private CalToken() {
    }

    public static CalToken parse(String valStr) throws Exception {
        //"+" "-" "*" "/"
        CalToken tmp = new CalToken();
        if ("+".equals(valStr)) {
            tmp.cal = (n1, n2) -> n1.doubleValue() + n2.doubleValue();
        }
        if ("-".equals(valStr)) {
            tmp.cal = (n1, n2) -> n1.doubleValue() - n2.doubleValue();
        }
        if ("*".equals(valStr)) {
            tmp.cal = (n1, n2) -> n1.doubleValue() * n2.doubleValue();
        }
        if ("/".equals(valStr)) {
            tmp.cal = (n1, n2) -> n1.doubleValue() / n2.doubleValue();
        }
        if (null == tmp.cal) {
            throw new TokenParseException();
        }
        return tmp;
    }

    public Number cal(Number n1, Number n2) {
        return cal.apply(n1, n2);
    }
}
