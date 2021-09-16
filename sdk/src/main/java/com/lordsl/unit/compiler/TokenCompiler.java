package com.lordsl.unit.compiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class TokenCompiler {

    public static Predicate<LogicContextModel> compile2Predicate(String plain) {
        return compile2Predicate(plain2Schema(plain));
    }

    public static Consumer<LogicContextModel> compile2Consumer(String plain) {
        return compile2Consumer(plain2Schema(plain));
    }

    public static Predicate<LogicContextModel> compile2Predicate(TokenSchema schema) {
        TokenInterpreter interpreter = TokenInterpreter.interpret(schema);
        return interpreter.getPredicate();
    }

    public static Consumer<LogicContextModel> compile2Consumer(TokenSchema schema) {
        TokenInterpreter interpreter = TokenInterpreter.interpret(schema);
        return interpreter.getConsumer();
    }

    public static String plain2JSON(String plain) {
        return schema2JSON(plain2Schema(plain));
    }

    public static String JSON2Plain(String json) {
        return schema2Plain(JSON2Schema(json));
    }

    public static TokenSchema plain2Schema(String plain) {
        TokenLexer lexer = TokenLexer.lex(plain);
        TokenParser parser = TokenParser.parse(lexer.getResult());
        return parser.getResult();
    }

    public static String schema2Plain(TokenSchema schema) {
        return ConvertUtil.schema2Plain(schema);
    }

    public static TokenSchema JSON2Schema(String json) {
        return JSON.toJavaObject(JSONObject.parseObject(json), TokenSchema.class);
    }

    public static String schema2JSON(TokenSchema schema) {
        return JSON.toJSON(schema).toString();
    }

    public static String norm(String plain) {
        return schema2Plain(plain2Schema(plain));
    }

}
