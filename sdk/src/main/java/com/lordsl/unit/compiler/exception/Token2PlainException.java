package com.lordsl.unit.compiler.exception;

public class Token2PlainException extends Exception {

    public Token2PlainException(String message) {
        super(message);
    }

    public Token2PlainException() {
        super("default message of Token2PlainException");
    }
}
