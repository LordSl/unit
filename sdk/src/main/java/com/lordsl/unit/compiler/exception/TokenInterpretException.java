package com.lordsl.unit.compiler.exception;

public class TokenInterpretException extends Exception {

    public TokenInterpretException(String message) {
        super(message);
    }

    public TokenInterpretException() {
        super("default message of TokenInterpretException");
    }
}
