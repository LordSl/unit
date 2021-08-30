package com.lordsl.unit.compiler.token;

import com.lordsl.unit.compiler.exception.TokenInterpretException;

import java.util.function.BiFunction;

public class CalToken {
    private BiFunction<Number, Number, Number> cal = null;

    private CalToken() {
    }

    public static CalToken interpret(String valStr) throws Exception {
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
            throw new TokenInterpretException();
        }
        return tmp;
    }

    public Number cal(Number n1, Number n2) {
        return cal.apply(n1, n2);
    }
}
