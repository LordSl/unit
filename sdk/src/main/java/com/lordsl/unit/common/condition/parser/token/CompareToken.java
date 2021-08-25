package com.lordsl.unit.common.condition.parser.token;

import java.util.function.BiFunction;

public class CompareToken {
    private BiFunction<Number, Number, Boolean> compare = null;

    private CompareToken() {
    }

    public static CompareToken parse(String valStr) throws Exception {
        //">" "<" "="
        CompareToken tmp = new CompareToken();
        if ("<".equals(valStr)) {
            tmp.compare = (n1, n2) -> n1.doubleValue() < n2.doubleValue();
        }
        if ("=".equals(valStr)) {
            tmp.compare = (n1, n2) -> n1.doubleValue() == n2.doubleValue();
        }
        if (">".equals(valStr)) {
            tmp.compare = (n1, n2) -> n1.doubleValue() > n2.doubleValue();
        }
        if (null == tmp.compare) {
            throw new TokenParseException();
        }
        return tmp;
    }

    public Boolean cal(NumToken v1, NumToken v2) {
        return compare.apply(v1.getValSupplier().get(), v2.getValSupplier().get());
    }
}
