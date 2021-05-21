package com.funix.capstone.exception.input;

public class BadInputException extends InputException{
    public BadInputException() {
    }

    public BadInputException(String message) {
        super(message);
    }

    public BadInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
