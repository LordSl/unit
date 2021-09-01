package com.lordsl.unit.compiler.token;

import com.lordsl.unit.compiler.exception.TokenInterpretException;

public class NumToken {

    private Number val;

    private NumToken() {
    }

    public static NumToken interpret(String valStr) throws Exception {
        NumToken tmp = new NumToken();
        try {
            tmp.val = interpretDoubleStr(valStr);
        } catch (Exception e) {
            return null;
        }
        return tmp;
    }

    private static Double interpretDoubleStr(String valStr) throws Exception {
        Double d = Double.parseDouble(valStr);
        if (d.isNaN())
            throw new TokenInterpretException("NumToken NaN");
        return d;
    }

    public Number getVal() {
        return val;
    }
}
