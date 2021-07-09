package net.timxekhach.generator.url.model;

import lombok.Getter;
import net.timxekhach.security.model.ApiMethod;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class RequestAnnotation {
    private final String requestType;
    private final Map<String, Class<?>> pathParams;
    private final String methodName;

    public RequestAnnotation(ApiMethod apiMethod) {
        this.requestType = getRequestAnnotationType(apiMethod.getRequestMethod());
        this.methodName = apiMethod.getName();
        this.pathParams = apiMethod.getPathVars();
    }

    private String getRequestAnnotationType(RequestMethod requestMethod) {
        if (requestMethod == RequestMethod.DELETE) {
            return "@DeleteMapping";
        }
        if(requestMethod == RequestMethod.GET) {
            return "@GetMapping";
        }
        if(requestMethod == RequestMethod.POST) {
            return "@PostMapping";
        }
        if(requestMethod == RequestMethod.PUT) {
            return "@PutMapping";
        }
        return "@RequestMapping";
    }

    @Override
    public String toString() {
        return String.format("\t%s(\"/%s%s\")",
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
}
