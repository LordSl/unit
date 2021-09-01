package com.lordsl.unit.compiler.token;

import com.lordsl.unit.util.Container;

import java.util.function.Consumer;

public class StatementToken {
    private Consumer<Container> containerConsumer;

    private StatementToken() {
    }

    public static StatementToken interpret(VarToken v1, VarToken v2) {
        StatementToken tmp = new StatementToken();
        tmp.containerConsumer = container -> container.put(v1.getName(), v2.getValSupplier().get());
        return tmp;
    }

    public Consumer<Container> getContainerConsumer() {
        return containerConsumer;
    }

}
