package com.funix.capstone.exception.input;

public class MandatoryException extends InputException {
    public MandatoryException() {
    }

    public MandatoryException(String message) {
        super(message);
    }

    public MandatoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
