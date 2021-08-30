package com.lordsl.unit.compiler.exception;

public class TokenParseException extends Exception {

    public TokenParseException(String message) {
        super(message);
    }

    public TokenParseException() {
        super("default message of TokenParseException");
    }
}
