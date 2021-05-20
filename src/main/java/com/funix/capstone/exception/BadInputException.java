package com.funix.capstone.exception;

public class BadInputException extends Exception{
    public BadInputException() {
        super();
    }

    public BadInputException(String message) {
        super(message);
    }

    public BadInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadInputException(Throwable cause) {
        super(cause);
    }
}
