package com.lordsl.unit.compiler.exception;

public class TokenLexException extends Exception {

    public TokenLexException(String message) {
        super(message);
    }

    public TokenLexException() {
        super("default message of TokenLexException");
    }
}
