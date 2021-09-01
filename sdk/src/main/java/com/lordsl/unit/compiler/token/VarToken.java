package com.lordsl.unit.compiler.token;

import java.util.function.Supplier;

public class VarToken {

    private Supplier<Number> valSupplier;
    private String name;

    private VarToken() {
    }

    public static VarToken interpret(String name, Supplier<Number> valSupplier) {
        VarToken tmp = new VarToken();
        tmp.name = name;
        tmp.valSupplier = valSupplier;
        return tmp;
    }

    public static VarToken interpret(NumToken n1) {
        VarToken tmp = new VarToken();
        tmp.valSupplier = n1::getVal;
        return tmp;
    }

    public static VarToken interpret(VarToken v1, CalToken c1, VarToken v2) {
        VarToken tmp = new VarToken();
        tmp.valSupplier = () -> c1.cal(v1.getValSupplier().get(), v2.getValSupplier().get());
        return tmp;
    }

    public Supplier<Number> getValSupplier() {
        return valSupplier;
    }

    public String getName() {
        return name;
    }
}
