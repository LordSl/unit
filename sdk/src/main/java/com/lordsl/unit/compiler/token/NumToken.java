package com.lordsl.unit.compiler.token;

import com.lordsl.unit.compiler.exception.TokenInterpretException;
import com.lordsl.unit.util.Info;

import java.util.function.Supplier;

public class NumToken {

    private Supplier<Number> valSupplier;

    private NumToken() {
    }

    public static NumToken interpret(String valStr) {
        NumToken tmp = new NumToken();
        tmp.valSupplier = () -> {
            try {
                return interpretDoubleStr(valStr);
            } catch (Exception e) {
                Info.PurpleAlert(e.getMessage());
                return null;
            }
        };
        return tmp;
    }

    private static Double interpretDoubleStr(String valStr) throws Exception {
        Double d = Double.parseDouble(valStr);
        if (d.isNaN())
            throw new TokenInterpretException("NumToken NaN");
        return d;
    }

    public static NumToken interpret(NumToken n1, CalToken c1, NumToken n2) {
        NumToken tmp = new NumToken();
        tmp.valSupplier = () -> c1.cal(n1.getValSupplier().get(), n2.getValSupplier().get());
        return tmp;
    }

    public static NumToken interpret(VarToken v1) {
        NumToken tmp = new NumToken();
        tmp.valSupplier = v1.getValSupplier();
        return tmp;
    }

    public Supplier<Number> getValSupplier() {
        return valSupplier;
    }
}
