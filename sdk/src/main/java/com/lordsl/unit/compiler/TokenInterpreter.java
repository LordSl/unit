package com.lordsl.unit.compiler;

import com.lordsl.unit.compiler.exception.TokenInterpretException;
import com.lordsl.unit.compiler.token.*;
import com.lordsl.unit.util.Container;
import com.lordsl.unit.util.Info;

import java.util.function.Predicate;


public class TokenInterpreter {

    private Container context;
    private Predicate<Container> result;

    private TokenInterpreter() {
    }

    public static TokenInterpreter interpret(TokenSchema input) {
        TokenInterpreter interpreter = new TokenInterpreter();
        try {
            ConditionToken conditionToken = interpreter.interpretCondition(input);
            interpreter.result = (env) -> {
                interpreter.context = env;
                return conditionToken.isTrue().get();
            };
        } catch (Exception e) {
            Info.PurpleAlert("interpret error");
        }
        return interpreter;
    }

    public Predicate<Container> getResult() {
        return result;
    }

    public ConditionToken interpretCondition(TokenSchema schema) throws Exception {
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
            //condition -> num compare num
            if (s1.typeNum() && s2.typeCompare() && s3.typeNum()) {
                return ConditionToken.interpret(interpretNum(s1), interpretCompare(s2), interpretNum(s3));
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

    public NumToken interpretNum(TokenSchema schema) throws Exception {
        if (!schema.typeNum())
            throw new TokenInterpretException();
        if (schema.subSize(1)) {
            TokenSchema s1 = schema.getSub().get(0);
            //num -> var
            if (s1.typeVar()) {
                return NumToken.interpret(interpretVar(s1));
            }
        }
        if (schema.subSize(3)) {
            TokenSchema s1 = schema.getSub().get(0);
            TokenSchema s2 = schema.getSub().get(1);
            TokenSchema s3 = schema.getSub().get(2);
            //num -> num cal num
            if (s1.typeNum() && s2.typeCal() && s3.typeNum()) {
                return NumToken.interpret(interpretNum(s1), interpretCal(s2), interpretNum(s3));
            }
            throw new TokenInterpretException();
        }
        return NumToken.interpret(schema.getVal());
    }

    public LogicToken interpretLogic(TokenSchema schema) throws Exception {
        if (schema.typeLogic() && schema.subSize(0)) {
            return LogicToken.interpret(schema.getVal());
        }
        throw new TokenInterpretException();
    }

    public VarToken interpretVar(TokenSchema schema) throws Exception {
        if (schema.typeVar() && schema.subSize(0)) {
            return VarToken.interpret(schema.getVal(), () -> context.get(schema.getVal()));
        }
        throw new TokenInterpretException();
    }

    public CompareToken interpretCompare(TokenSchema schema) throws Exception {
        if (schema.typeCompare() && schema.subSize(0)) {
            return CompareToken.interpret(schema.getVal());
        }
        throw new TokenInterpretException();
    }

    public CalToken interpretCal(TokenSchema schema) throws Exception {
        if (schema.typeCal() && schema.subSize(0)) {
            return CalToken.interpret(schema.getVal());
        }
        throw new TokenInterpretException();

    }
}
