package com.lordsl.unit.common.condition.parser;

import com.lordsl.unit.common.condition.parser.token.*;
import com.lordsl.unit.common.util.Container;
import com.lordsl.unit.common.util.Info;

import java.util.function.Predicate;

public class ConditionParser {

    private Container context;//上下文
    private ConditionToken result;

    private ConditionParser() {
    }

    public static ConditionParser parse(TokenSchema schema) {
        ConditionParser parser = new ConditionParser();
        try {
            parser.result = parser.parseCondition(schema);
        } catch (Exception e) {
            Info.PurpleAlert("parse error");
        }
        return parser;
    }

    public Predicate<Container> getPredicate() {
        return this::apply;
    }

    public Boolean apply(Container context) {
        this.context = context;
        return this.result.isTrue().get();
    }

    public ConditionToken parseCondition(TokenSchema schema) throws Exception {
        if (!schema.ConditionType())
            throw new TokenParseException();
        if (schema.subSize(3)) {
            TokenSchema s1 = schema.getSub().get(0);
            TokenSchema s2 = schema.getSub().get(1);
            TokenSchema s3 = schema.getSub().get(2);
            //condition -> condition logic condition
            if (s1.ConditionType() && s2.LogicType() && s3.ConditionType()) {
                return ConditionToken.parse(parseCondition(s1), parseLogic(s2), parseCondition(s3));
            }
            //condition -> num compare num
            if (s1.NumType() && s2.CompareType() && s3.NumType()) {
                return ConditionToken.parse(parseNum(s1), parseCompare(s2), parseNum(s3));
            }
        }
        if (schema.subSize(2)) {
            TokenSchema s1 = schema.getSub().get(0);
            TokenSchema s2 = schema.getSub().get(1);
            //condition -> logic condition
            if (s1.LogicType() && s2.ConditionType()) {
                return ConditionToken.parse(parseLogic(s1), parseCondition(s2));
            }
        }
        throw new TokenParseException();
    }

    public NumToken parseNum(TokenSchema schema) throws Exception {
        if (!schema.NumType())
            throw new TokenParseException();
        if (schema.subSize(1)) {
            TokenSchema s1 = schema.getSub().get(0);
            //num -> var
            if (s1.VarType()) {
                return NumToken.parse(parseVar(s1));
            }
        }
        if (schema.subSize(3)) {
            TokenSchema s1 = schema.getSub().get(0);
            TokenSchema s2 = schema.getSub().get(1);
            TokenSchema s3 = schema.getSub().get(2);
            //num -> num cal num
            if (s1.NumType() && s2.CalType() && s3.NumType()) {
                return NumToken.parse(parseNum(s1), parseCal(s2), parseNum(s3));
            }
            throw new TokenParseException();
        }
        return NumToken.parse(schema.getVal());
    }

    public LogicToken parseLogic(TokenSchema schema) throws Exception {
        if (schema.LogicType() && schema.subSize(0)) {
            return LogicToken.parse(schema.getVal());
        }
        throw new TokenParseException();
    }

    public VarToken parseVar(TokenSchema schema) throws Exception {
        if (schema.VarType() && schema.subSize(0)) {
            return VarToken.parse(schema.getVal(), () -> context.get(schema.getVal()));
        }
        throw new TokenParseException();
    }

    public CompareToken parseCompare(TokenSchema schema) throws Exception {
        if (schema.CompareType() && schema.subSize(0)) {
            return CompareToken.parse(schema.getVal());
        }
        throw new TokenParseException();
    }

    public CalToken parseCal(TokenSchema schema) throws Exception {
        if (schema.CalType() && schema.subSize(0)) {
            return CalToken.parse(schema.getVal());
        }
        throw new TokenParseException();

    }
}
