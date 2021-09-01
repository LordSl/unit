package com.lordsl.unit.compiler;

import com.lordsl.unit.util.Info;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class TokenParser {
    private TokenSchema result;

    private TokenParser() {
    }

    private final static Map<List<String>, String> rules = new LinkedHashMap<List<String>, String>() {{
        put(Arrays.asList(Token.Num), Token.Var);
        put(Arrays.asList(Token.Condition, Token.Logic, Token.Condition), Token.Condition);
        put(Arrays.asList(Token.Logic, Token.Condition), Token.Condition);
        put(Arrays.asList(Token.Var, Token.Compare, Token.Var), Token.Condition);
        put(Arrays.asList(Token.Var, Token.Cal, Token.Var), Token.Var);
        put(Arrays.asList(Token.LP, Token.Var, Token.RP), Token.Var);
        put(Arrays.asList(Token.LP, Token.Condition, Token.RP), Token.Condition);
        put(Arrays.asList(Token.LP, Token.Num, Token.RP), Token.Num);
        put(Arrays.asList(Token.Var, Token.Init, Token.Var), Token.Statement);
    }};

    public TokenSchema getResult() {
        return result;
    }

    public static TokenParser parse(List<TokenSchema> input) {
        TokenParser parser = new TokenParser();
        try {
            parser.result = parser.parseAll(input);
        } catch (Exception e) {
            Info.PurpleAlert("parse error");
        }
        return parser;
    }

    private static <T> void replace(List<T> list, int begin, int end, T element) {
        List<T> newList = new ArrayList<>(list.subList(0, begin));
        newList.add(element);
        newList.addAll(list.subList(end, list.size()));
        list.clear();
        list.addAll(newList);
    }

    private TokenSchema parseAll(List<TokenSchema> list) {
        while (parseOne(list)) ;
        if (list.size() == 1)
            return list.get(0);
        else return null;
    }

    private boolean parseOne(List<TokenSchema> list) {
        AtomicBoolean doParse = new AtomicBoolean(false);
        rules.forEach(
                (caseKey, caseVal) -> {
                    int lastSize = -1;
                    while (lastSize != list.size()) {
                        lastSize = list.size();
                        List<Integer> d = match(list, caseKey, TokenSchema::getType);
                        if (null != d) {
                            int left = d.get(0);
                            int right = d.get(1);
                            replace(list, left, right, poly(TokenSchema.create().type(caseVal).addSubAll(list.subList(left, right))));
                            doParse.set(true);
                        }
                    }

                }
        );
        return doParse.get();
    }

    private TokenSchema poly(TokenSchema schema) {
        if (schema.subSize(3)) {
            TokenSchema s1 = schema.getSub().get(0);
            TokenSchema s2 = schema.getSub().get(1);
            TokenSchema s3 = schema.getSub().get(2);
            //处理括号
            //{any} -> (lp) {any} (rp)
            if (s1.typeLP() && s3.typeRP()) {
                schema.setType(s2.getType());
                schema.setVal(s2.getVal());
                schema.setSub(s2.getSub());
            }
            //处理赋值
            //statement -> var (ini) var
            if (s1.typeVar() && s2.typeInit() && s3.typeVar()) {
                schema.getSub().remove(s2);
            }
        }
        return schema;
    }

    private <T, R> List<Integer> match(List<T> list, List<String> part, Function<T, String> func) {
        if (list.size() < part.size()) return null;
        for (int i = 0; i <= list.size() - part.size(); i++) {
            boolean isMatch = true;
            for (int j = 0; j < part.size(); j++) {
                if (!func.apply(list.get(i + j)).equals(part.get(j))) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                return Arrays.asList(i, i + part.size());
            }
        }
        return null;
    }

}