package com.lordsl.unit.compiler;

import java.util.ArrayList;
import java.util.List;

public class TokenSchema {
    private String type;
    private String val;
    private List<TokenSchema> sub = new ArrayList<>();

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TokenSchema> getSub() {
        return sub;
    }

    public void setSub(List<TokenSchema> sub) {
        this.sub = sub;
    }

    public Boolean subSize(Integer expect) {
        return expect.equals(sub.size());
    }

    public static TokenSchema create() {
        return new TokenSchema();
    }

    public boolean typeCondition() {
        return this.getType().equals(Token.Condition);
    }

    public boolean typeLogic() {
        return this.getType().equals(Token.Logic);
    }

    public boolean typeCompare() {
        return this.getType().equals(Token.Compare);
    }

    public boolean typeCal() {
        return this.getType().equals(Token.Cal);
    }

    public boolean typeNum() {
        return this.getType().equals(Token.Num);
    }

    public boolean typeVar() {
        return this.getType().equals(Token.Var);
    }

    public boolean typeLP() {
        return this.getType().equals(Token.LP);
    }

    public boolean typeRP() {
        return this.getType().equals(Token.RP);
    }

    public TokenSchema val(String valStr) {
        this.setVal(valStr);
        return this;
    }

    public TokenSchema type(String Type) {
        this.setType(Type);
        return this;
    }

    public TokenSchema addSub(TokenSchema schema) {
        this.sub.add(schema);
        return this;
    }

    public TokenSchema addSubAll(List<TokenSchema> schemaList) {
        this.sub.addAll(schemaList);
        return this;
    }

}
