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

    public Supplier<Number> getValSupplier() {
        return valSupplier;
    }

    public String getName() {
        return name;
    }
}
