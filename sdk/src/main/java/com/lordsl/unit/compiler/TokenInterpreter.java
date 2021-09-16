package com.lordsl.unit.compiler;

import com.lordsl.unit.compiler.exception.TokenInterpretException;
import com.lordsl.unit.compiler.token.*;
import com.lordsl.unit.util.Info;

import java.util.function.Consumer;
import java.util.function.Predicate;


public class TokenInterpreter {

    private LogicContextModel context;
    private Predicate<LogicContextModel> predicate;
    private Consumer<LogicContextModel> consumer;

    private TokenInterpreter() {
    }

    public static TokenInterpreter interpret(TokenSchema input) {
        TokenInterpreter interpreter = new TokenInterpreter();
        try {
            if (input.typeCondition()) {
                ConditionToken conditionToken = interpreter.interpretCondition(input);
                interpreter.predicate = (env) -> {
                    interpreter.context = env;
                    return conditionToken.getBoolSupplier().get();
                };
            }
            if (input.typeStatement()) {
                StatementToken statementToken = interpreter.interpretStatement(input);
                interpreter.consumer = (env) -> {
                    interpreter.context = env;
                    statementToken.getContainerConsumer().accept(env);
                };
            }
            if (null == interpreter.consumer && null == interpreter.predicate)
                throw new TokenInterpretException();
        } catch (Exception e) {
            Info.PurpleAlert("interpret error");
        }
        return interpreter;
    }

    public Consumer<LogicContextModel> getConsumer() {
        return consumer;
    }

    public Predicate<LogicContextModel> getPredicate() {
        return predicate;
    }

    private StatementToken interpretStatement(TokenSchema schema) throws Exception {
        if (!schema.typeStatement())
            throw new TokenInterpretException();
        if (schema.subSize(2)) {
            TokenSchema s1 = schema.getSub().get(0);
            TokenSchema s2 = schema.getSub().get(1);
            //statement -> var var
            if (s1.typeVar() && s2.typeVar()) {
                return StatementToken.interpret(interpretVar(s1), interpretVar(s2));
            }
        }
        throw new TokenInterpretException();
    }

    private ConditionToken interpretCondition(TokenSchema schema) throws Exception {
        if (!schema.typeCondition())
            throw new TokenInterpretException();
        if (schema.subSize(3)) {
            TokenSchema s1 = schema.getSub().get(0);
            TokenSchema s2 = schema.getSub().get(1);
            TokenSchema s3 = schema.getSub().get(2);
            //condition -> condition logic condition
            if (s1.typeCondition() && s2.typeLogic() && s3.typeCondition()) {
                return ConditionToken.interpret(interpretCondition(s1), interpretLogic(s2), interpretCondition(s3));
            }
            //condition -> var compare var
            if (s1.typeVar() && s2.typeCompare() && s3.typeVar()) {
                return ConditionToken.interpret(interpretVar(s1), interpretCompare(s2), interpretVar(s3));
            }
        }
        if (schema.subSize(2)) {
            TokenSchema s1 = schema.getSub().get(0);
            TokenSchema s2 = schema.getSub().get(1);
            //condition -> logic condition
            if (s1.typeLogic() && s2.typeCondition()) {
                return ConditionToken.interpret(interpretLogic(s1), interpretCondition(s2));
            }
        }
        throw new TokenInterpretException();
    }

    private VarToken interpretVar(TokenSchema schema) throws Exception {
        if (!schema.typeVar())
            throw new TokenInterpretException();
        if (schema.subSize(1)) {
            TokenSchema s1 = schema.getSub().get(0);
            //var -> num
            if (s1.typeNum()) {
                return VarToken.interpret(interpretNum(s1));
            }
        }
        if (schema.subSize(3)) {
            TokenSchema s1 = schema.getSub().get(0);
            TokenSchema s2 = schema.getSub().get(1);
            TokenSchema s3 = schema.getSub().get(2);
            //var -> var cal var
            if (s1.typeVar() && s2.typeCal() && s3.typeVar()) {
                return VarToken.interpret(interpretVar(s1), interpretCal(s2), interpretVar(s3));
            }
            throw new TokenInterpretException();
        }
        return VarToken.interpret(schema.getVal(), () -> context.get(schema.getVal()));
    }

    private LogicToken interpretLogic(TokenSchema schema) throws Exception {
        if (schema.typeLogic() && schema.subSize(0)) {
            return LogicToken.interpret(schema.getVal());
        }
        throw new TokenInterpretException();
    }

    private NumToken interpretNum(TokenSchema schema) throws Exception {
        if (schema.typeNum() && schema.subSize(0)) {
            return NumToken.interpret(schema.getVal());
        }
        throw new TokenInterpretException();
    }

    private CompareToken interpretCompare(TokenSchema schema) throws Exception {
        if (schema.typeCompare() && schema.subSize(0)) {
            return CompareToken.interpret(schema.getVal());
        }
        throw new TokenInterpretException();
    }

    private CalToken interpretCal(TokenSchema schema) throws Exception {
        if (schema.typeCal() && schema.subSize(0)) {
            return CalToken.interpret(schema.getVal());
        }
        throw new TokenInterpretException();

    }
}
