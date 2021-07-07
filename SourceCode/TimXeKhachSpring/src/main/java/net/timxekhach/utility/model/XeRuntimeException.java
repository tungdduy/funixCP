package net.timxekhach.utility.model;

import lombok.Getter;

@Getter
public class XeRuntimeException extends RuntimeException {
    private Message xeMessage;
    public XeRuntimeException(Message message) {
        this();
        this.xeMessage = message;
    }

    public XeRuntimeException(){
        super("XeRuntimeException");
    }
}
