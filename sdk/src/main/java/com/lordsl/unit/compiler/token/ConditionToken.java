package com.lordsl.unit.compiler.token;

import java.util.function.Supplier;

public class ConditionToken {

    private Supplier<Boolean> booleanSupplier;

    private ConditionToken() {
    }

    public static ConditionToken interpret(VarToken v1, CompareToken compare, VarToken v2) {
        ConditionToken tmp = new ConditionToken();
        tmp.booleanSupplier = () -> compare.cal(v1, v2);
        return tmp;
    }

    public static ConditionToken interpret(ConditionToken c1, LogicToken logic, ConditionToken c2) {
        ConditionToken tmp = new ConditionToken();
        tmp.booleanSupplier = () -> logic.cal(c1, c2);
        return tmp;
    }

    public static ConditionToken interpret(LogicToken logic, ConditionToken c1) {
        ConditionToken tmp = new ConditionToken();
        tmp.booleanSupplier = () -> logic.cal(c1);
        return tmp;
    }

    public Supplier<Boolean> getBoolSupplier() {
        return booleanSupplier;
    }
}
