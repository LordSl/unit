package com.lordsl.unit.compiler.token;

import com.lordsl.unit.compiler.LogicContextModel;

import java.util.function.Consumer;

public class StatementToken {
    private Consumer<LogicContextModel> containerConsumer;

    private StatementToken() {
    }

    public static StatementToken interpret(VarToken v1, VarToken v2) {
        StatementToken tmp = new StatementToken();
        tmp.containerConsumer = container -> container.put(v1.getName(), v2.getValSupplier().get());
        return tmp;
    }

    public Consumer<LogicContextModel> getContainerConsumer() {
        return containerConsumer;
    }

}
