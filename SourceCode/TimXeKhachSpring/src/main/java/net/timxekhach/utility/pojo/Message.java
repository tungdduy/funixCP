package net.timxekhach.utility.pojo;

import lombok.Getter;
import net.timxekhach.operation.response.ErrorCode;

import java.util.List;

@Getter
public class Message {
    String messageCode;
    String[] params;

    public Message(String messageCode, List<String> params) {
        this.messageCode = messageCode;
        this.params = params.toArray(new String[0]);
    }

    public Message(ErrorCode errorCode, String[] params) {
        this.messageCode = errorCode.name();
        this.params = params;
    }
}

