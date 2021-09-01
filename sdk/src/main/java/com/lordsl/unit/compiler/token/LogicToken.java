package com.lordsl.unit.compiler.token;

import com.lordsl.unit.compiler.exception.TokenInterpretException;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class LogicToken {
    Predicate<Boolean> cal = null;
    BiPredicate<Boolean, Boolean> biCal = null;

    private LogicToken() {
    }

    public static LogicToken interpret(String valStr) throws Exception {
        //"&" "|" "!"
        LogicToken tmp = new LogicToken();
        if ("&".equals(valStr)) {
            tmp.cal = b1 -> b1;
            tmp.biCal = (b1, b2) -> b1 && b2;
        }
        if ("|".equals(valStr)) {
            tmp.cal = b1 -> true;
            tmp.biCal = (b1, b2) -> b1 || b2;
        }
        if ("!".equals(valStr)) {
            tmp.cal = b1 -> !b1;
            tmp.biCal = (b1, b2) -> b1 != b2;
        }
        if (null == tmp.cal) {
            throw new TokenInterpretException();
        }
        return tmp;
    }

    public Boolean cal(ConditionToken c1, ConditionToken c2) {
        return biCal.test(c1.getBoolSupplier().get(), c2.getBoolSupplier().get());
    }

    public Boolean cal(ConditionToken c1) {
        return cal.test(c1.getBoolSupplier().get());
    }

}
