package com.funix.capstone.exception;

public class MandatoryException extends Exception{
    public MandatoryException() {
        super();
    }

    public MandatoryException(String message) {
        super(message);
    }

    public MandatoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
