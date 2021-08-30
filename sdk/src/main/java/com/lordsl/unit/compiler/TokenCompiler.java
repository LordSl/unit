package com.lordsl.unit.compiler;

import com.lordsl.unit.util.Container;

import java.util.function.Predicate;

public class TokenCompiler {

    public static Predicate<Container> compile2Predicate(String input) {
        TokenLexer lexer = TokenLexer.lex(input);
        TokenParser parser = TokenParser.parse(lexer.getResult());
        TokenInterpreter interpreter = TokenInterpreter.interpret(parser.getResult());
        return interpreter.getResult();
    }
}
