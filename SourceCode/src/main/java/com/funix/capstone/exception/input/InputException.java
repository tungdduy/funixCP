package com.funix.capstone.exception.input;

public abstract class InputException extends Exception {
    public InputException() {
    }

    public InputException(String message) {
        super(message);
    }

    public InputException(String message, Throwable cause) {
        super(message, cause);
    }
}
