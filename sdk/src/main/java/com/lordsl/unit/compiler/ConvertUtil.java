package com.lordsl.unit.compiler;

import com.lordsl.unit.compiler.exception.Token2PlainException;
import com.lordsl.unit.util.Info;

import java.util.List;

class ConvertUtil {

    static String schema2Plain(TokenSchema schema) {
        try {
            return norm(recur(schema));
        } catch (Exception e) {
            Info.PurpleAlert("schema to plain trans error");
            return null;
        }
    }

    private static String norm(String s) {
        while (s.contains("  ")) {
            s = s.replace("  ", " ");
        }
        while (' ' == s.charAt(0))
            s = s.substring(1);
        return s;
    }

    private static String recur(TokenSchema schema) throws Exception {
        if (schema.typeVar() && schema.subSize(1)) {
            TokenSchema s1 = schema.getSub().get(0);
            if (s1.typeNum()) {
                return recur(s1);
            }
        }
        if (schema.typeVar() && schema.subSize(1) && schema.getSub().get(0).typeNum()) {
            return recur(schema.getSub().get(0));
        }

        if (schema.subSize(2)) {
            List<TokenSchema> sub = schema.getSub();
            TokenSchema s1 = schema.getSub().get(0);
            TokenSchema s2 = schema.getSub().get(1);
            if (s1.typeVar() && s2.typeVar()) {
                return String.format("(%s := %s )", recur(s1), recur(s2));
            }
        }
        if (schema.subSize(0) && null != schema.getVal()) {
            return " " + schema.getVal();
        }
        if (null == schema.getVal()) {
            StringBuilder res = new StringBuilder();
            for (TokenSchema sub : schema.getSub()) {
                res.append(recur(sub));
            }
            return String.format(" (%s )", res);
        }
        throw new Token2PlainException();
    }

}
