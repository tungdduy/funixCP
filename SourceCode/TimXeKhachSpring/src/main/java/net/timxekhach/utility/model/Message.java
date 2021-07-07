package net.timxekhach.utility.model;

import lombok.Getter;
import net.timxekhach.operation.response.ErrorCode;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Message {
    String code;
    Map<String, String> params = new HashMap<>();

    public Message(ErrorCode errorCode, String... paramValues) {
        this.code = errorCode.name();
        String[] paramNames = errorCode.getParamNames();
        if(errorCode.getParamNames() != null && paramValues != null) {
            int paramSize = Math.min(paramNames.length, paramValues.length);
            for(int i = 0; i < paramSize; i++) {
                params.put(paramNames[i], paramValues[i]);
            }
        }
    }

}

