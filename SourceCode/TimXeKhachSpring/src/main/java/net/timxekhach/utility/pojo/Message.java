package net.timxekhach.utility.pojo;

import lombok.Getter;
import lombok.NonNull;
import net.timxekhach.operation.response.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Message {
    String messageCode;
    List<Param> params = new ArrayList<>();

    public Message(ErrorCode errorCode, String... paramValues) {
        this.messageCode = errorCode.name();
        if(paramValues != null) {
            for(int i = 0; i < paramValues.length; i++) {
                String paramName = "UNDEFINED";
                if(errorCode.getParamNames() != null && errorCode.getParamNames().length > i) {
                    paramName = errorCode.getParamNames()[i];
                }
                Param param = new Param(paramName, paramValues[i]);
                params.add(param);
            }
        }
    }



    @Getter
    private class Param {
        Param(String name, String value) {
            this.name = name;
            this.value = value;
        }
        private String name;
        private String value;
    }
}

