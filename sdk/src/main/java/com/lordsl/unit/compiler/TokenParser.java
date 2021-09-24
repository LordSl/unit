package com.lordsl.unit.compiler;

import com.lordsl.unit.util.Info;

import java.util.*;

public class TokenParser {
    private final static Map<String, List<List<String>>> rules = new LinkedHashMap<String, List<List<String>>>() {{
        put(Token.Top, Arrays.asList(
                Collections.singletonList(Token.Condition),
                Collections.singletonList(Token.Statement)
        ));
        put(Token.Statement, Arrays.asList(
                Arrays.asList(Token.LP, Token.Statement, Token.RP),
                Arrays.asList(Token.Var, Token.Init, Token.Var)
        ));
        put(Token.Condition, Arrays.asList(
                Arrays.asList(Token.LP, Token.Condition, Token.RP),
                Arrays.asList(Token.Logic, Token.Condition),
                Arrays.asList(Token.Var, Token.Compare, Token.Var),
                Arrays.asList(Token.LP, Token.Condition, Token.Logic, Token.Condition, Token.RP)
        ));
        put(Token.Var, Arrays.asList(
                Arrays.asList(Token.LP, Token.Var, Token.RP),
                Collections.singletonList(Token.Num),
                Arrays.asList(Token.LP, Token.Var, Token.Cal, Token.Var, Token.RP)
        ));
    }};

    private TokenSchema result;

    private TokenParser() {
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

    private TokenSchema parseAll(List<TokenSchema> list) {
        TokenSchema top = explainAs(list, Token.Top);
        if (null != top && top.subSize(1)) {
            delUselessRecur(top.getSub().get(0));
            return top.getSub().get(0);
        }
        return null;
    }

    private TokenSchema explainAs(List<TokenSchema> schemaList, String expectType) {
        List<List<String>> allCase = rules.getOrDefault(expectType, new ArrayList<>());
        TokenSchema ret = TokenSchema.create().type(expectType);

        if (schemaList.get(0).getType().equals(expectType))
            return ret.addSub(schemaList.get(0));

        for (List<String> oneCase : allCase) {
            ret.getSub().clear();
            List<TokenSchema> copy = new ArrayList<>(schemaList);
            boolean success = true;
            for (String type : oneCase) {
                TokenSchema child = explainAs(copy, type);
                if (null == child) {
                    success = false;
                    break;
                }
                ret.getSub().add(child);
                copy = copy.subList(cntLeaf(child), copy.size());
            }
            if (success) {
                return ret;
            }
        }
        return null;
    }

    private int cntLeaf(TokenSchema schema) {
        if (schema.subSize(0))
            return 1;
        else {
            int cnt = 0;
            for (TokenSchema t : schema.getSub())
                cnt += cntLeaf(t);
            return cnt;
        }
    }


    private void delUselessRecur(TokenSchema schema) {
        for (TokenSchema t : schema.getSub())
            delUselessRecur(t);
        delUseless(schema);
    }

    private void delUseless(TokenSchema schema) {
        List<TokenSchema> use = new ArrayList<>();
        schema.getSub().forEach(t -> {
            if (!(t.typeLP() || t.typeRP() || t.typeInit()))
                use.add(t);
        });
        schema.setSub(use);
        while (schema.subSize(1) && schema.getType().equals(schema.getSub().get(0).getType())) {
            schema.setVal(schema.getSub().get(0).getVal());
            schema.setSub(schema.getSub().get(0).getSub());
        }
    }

    public TokenSchema getResult() {
        return result;
    }

}