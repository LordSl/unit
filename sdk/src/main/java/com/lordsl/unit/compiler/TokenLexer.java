package com.lordsl.unit.compiler;

import com.lordsl.unit.compiler.exception.TokenLexException;
import com.lordsl.unit.util.Info;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenLexer {
    private final static Map<String, String> lexMap = new HashMap<String, String>() {{
        put(Token.Logic, Token.Regex.Logic);
        put(Token.Compare, Token.Regex.Compare);
        put(Token.Cal, Token.Regex.Cal);
        put(Token.Num, Token.Regex.Num);
        put(Token.Var, Token.Regex.Var);
        put(Token.LP, Token.Regex.LP);
        put(Token.RP, Token.Regex.RP);
        put(Token.Init, Token.Regex.Init);
    }};
    private List<TokenSchema> result;

    private TokenLexer() {
    }

    private static Boolean match(String regex, String input) {
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(input);
        return m.matches();
    }

    public static TokenLexer lex(String input) {
        TokenLexer lexer = new TokenLexer();
        try {
            lexer.result = lexer.lexStr(input);
        } catch (Exception e) {
            Info.PurpleAlert("lex error");
        }
        return lexer;
    }

    public List<TokenSchema> getResult() {
        return result;
    }

    private List<TokenSchema> lexStr(String input) throws Exception {
        List<String> inputList = Arrays.asList(input.split(" "));
        List<TokenSchema> schemaList = new ArrayList<>();
        inputList.forEach(
                valStr -> lexMap.forEach(
                        (type, regex) -> {
                            if (match(regex, valStr)) {
                                schemaList.add(TokenSchema.create()
                                        .type(type)
                                        .val(valStr));
                            }
                        }
                )
        );
        if (schemaList.size() != inputList.size())
            throw new TokenLexException();
        return schemaList;
    }
}
