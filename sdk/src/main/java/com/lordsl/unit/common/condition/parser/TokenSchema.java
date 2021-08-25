package com.lordsl.unit.common.condition.parser;

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

    public boolean ConditionType() {
        return this.getType().equals(TokenType.Condition);
    }

    public boolean LogicType() {
        return this.getType().equals(TokenType.Logic);
    }

    public boolean CompareType() {
        return this.getType().equals(TokenType.Compare);
    }

    public boolean CalType() {
        return this.getType().equals(TokenType.Cal);
    }

    public boolean NumType() {
        return this.getType().equals(TokenType.Num);
    }

    public boolean VarType() {
        return this.getType().equals(TokenType.Var);
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

}
