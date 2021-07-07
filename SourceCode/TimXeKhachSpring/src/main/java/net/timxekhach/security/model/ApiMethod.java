package net.timxekhach.security.model;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApiMethod {
    private final Map<String, Class<?>> parameters = new HashMap<>();
    private Class<?> returnType;
    private String name;
    private final UrlNode caller;
    private final Map<String, Class<?>> pathVars = new HashMap<>();
    public ApiMethod(UrlNode caller, String name) {
        this.caller = caller;
        caller.getMethods().add(this);
        this.name = name;
    }
    public ApiMethod param(String name, Class<?> paramType) {
        parameters.put(name, paramType);
        return this;
    }
    public ApiMethod type(Class<?> type) {
        this.returnType = type;
        return this;
    }

    public ApiMethod pathVar(String name, Class<?> type) {
        pathVars.put(name, type);
        return this;
    }

    public UrlNode caller() {
        return this.caller;
    }

    public ApiMethod method(String name) {
        return new ApiMethod(this.caller, name);
    }

    public UrlNode create(String name) {
        return this.caller.create(name);
    }
}
