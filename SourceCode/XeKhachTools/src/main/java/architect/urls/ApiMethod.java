package architect.urls;


import generator.builders.ApiMethodBuilder;
import generator.abstracts.interfaces.AuthConfig;
import lombok.Getter;
import net.timxekhach.security.constant.RoleEnum;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Getter
public class ApiMethod implements AuthConfig {
    private final Map<String, Class<?>> parameters = new HashMap<>();
    private Class<?> returnType;
    private final String url;
    private final UrlNode caller;
    private final Map<String, Class<?>> pathVars = new HashMap<>();
    private final RequestMethod requestMethod = RequestMethod.POST;
    private List<RoleEnum> roles = new ArrayList<>();
    private Boolean isPublic;

    public ApiMethod(UrlNode caller, String url) {
        this.caller = caller;
        caller.getMethods().add(this);
        this.url = url;
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
    public ApiMethod method(String name) {
        return new ApiMethod(this.caller, name);
    }

    public UrlNode create(String name) {
        return this.caller.create(name);
    }

    private ApiMethodBuilder builder;
    public ApiMethodBuilder getBuilder() {
        if(this.builder == null) {
            this.builder = new ApiMethodBuilder(this);
        }
        return this.builder;
    }

    public ApiMethod roles(RoleEnum... roles) {
        this.roles = Arrays.asList(roles);
        return this;
    }
    ApiMethod PUBLIC() {
        this.isPublic = true;
        return this;
    }
}
