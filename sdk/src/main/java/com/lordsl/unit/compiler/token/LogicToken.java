package com.lordsl.unit.compiler.token;

import com.lordsl.unit.compiler.exception.TokenInterpretException;

import java.util.function.BiPredicate;

public class LogicToken {
    BiPredicate<Boolean, Boolean> cal = null;

    private LogicToken() {
    }

    public static LogicToken interpret(String valStr) throws Exception {
        //"&" "|" "!"
        LogicToken tmp = new LogicToken();
        if ("&".equals(valStr)) {
            tmp.cal = (b1, b2) -> b1 && b2;
        }
        if ("|".equals(valStr)) {
            tmp.cal = (b1, b2) -> b1 || b2;
        }
        if ("!".equals(valStr)) {
            tmp.cal = (b1, b2) -> !b2;
        }
        if (null == tmp.cal) {
            throw new TokenInterpretException();
        }
        return tmp;
    }

    public Boolean cal(ConditionToken c1, ConditionToken c2) {
        return cal.test(c1.isTrue().get(), c2.isTrue().get());
    }

    public Boolean cal(ConditionToken c1) {
        return cal.test(null, c1.isTrue().get());
    }

}
