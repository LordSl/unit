package com.lordsl.unit.common.condition.parser.token;

public class TokenParseException extends Exception {

    public TokenParseException(String message) {
        super(message);
    }

    public TokenParseException() {
        super("default message of TokenParseException");
    }
}
