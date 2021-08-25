package com.lordsl.unit.common.condition.parser.token;

import java.util.function.Supplier;

public class ConditionToken {

    private Supplier<Boolean> booleanSupplier;

    private ConditionToken() {
    }

    public static ConditionToken parse(NumToken n1, CompareToken compare, NumToken n2) {
        ConditionToken tmp = new ConditionToken();
        tmp.booleanSupplier = () -> compare.cal(n1, n2);
        return tmp;
    }

    public static ConditionToken parse(ConditionToken c1, LogicToken logic, ConditionToken c2) {
        ConditionToken tmp = new ConditionToken();
        tmp.booleanSupplier = () -> logic.cal(c1, c2);
        return tmp;
    }

    public static ConditionToken parse(LogicToken logic, ConditionToken c1) {
        ConditionToken tmp = new ConditionToken();
        tmp.booleanSupplier = () -> logic.cal(c1);
        return tmp;
    }

    public Supplier<Boolean> isTrue() {
        return booleanSupplier;
    }
}
