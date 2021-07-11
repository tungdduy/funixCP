package net.timxekhach.generator.builders;

import lombok.Getter;
import net.timxekhach.security.model.ApiMethod;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Getter
public class ApiMethodAnnotationBuilder {
    private final String requestType;
    private final Map<String, Class<?>> pathParams;
    private final String methodName;
    private final List<Class<?>> allClasses = new ArrayList<>();
    private static Map<RequestMethod, Class> requestAnnotationType;

    public ApiMethodAnnotationBuilder(ApiMethod apiMethod) {
        this.requestType = getRequestType(apiMethod.getRequestMethod()).getSimpleName();
        this.methodName = apiMethod.getUrl();
        this.pathParams = apiMethod.getPathVars();
    }

    private Class<?> getRequestType(RequestMethod requestMethod) {
        if (requestAnnotationType == null) {
            requestAnnotationType = new HashMap<>();
            requestAnnotationType.put(RequestMethod.DELETE, DeleteMapping.class);
            requestAnnotationType.put(RequestMethod.GET, GetMapping.class);
            requestAnnotationType.put(RequestMethod.POST, PostMapping.class);
            requestAnnotationType.put(RequestMethod.PUT, PutMapping.class);
        }
        return Optional.ofNullable(requestAnnotationType.get(requestMethod))
                .orElse(RequestMapping.class);
    }

    public String buildFullString() {
        return String.format("\t@%s(\"/%s%s\")",
                this.getRequestType(),
                this.methodName,
                this.buildPathParamsString());
    }

    private String buildPathParamsString() {
        List<String> pathVarUrls = new ArrayList<>();
        this.pathParams.forEach((name, type) -> {
            pathVarUrls.add(String.format("/{%s}", name));
        });
        return String.join("", pathVarUrls);
    }

    public List<String> allImportClasses() {
        return Collections.singletonList("org.springframework.web.bind.annotation.*");
    }
}
