package net.timxekhach.generator.url;

import net.timxekhach.generator.url.model.RequestAnnotation;
import net.timxekhach.security.model.ApiMethod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ApiMethodBuilder {

    private final ApiMethod apiMethod;

    @SuppressWarnings("rawtypes")
    public Class[] getAllParamTypes(){
        List<Class> paramClasses = new ArrayList<>();
        paramClasses.addAll(apiMethod.getParameters().values());
        paramClasses.addAll(apiMethod.getPathVars().values());
        return paramClasses.toArray(new Class[0]);
    }

    public ApiMethodBuilder(ApiMethod apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String buildBodyLines() {
        List<String> bodyLines = new ArrayList<>();

        String arguments = buildArguments();

        if(apiMethod.getReturnType() != null) {
            bodyLines.add(String.format("\t\treturn XeResponseUtils.success(%s);",  buildServiceCall(arguments)));
        } else {
            bodyLines.add(String.format("\t\t%s;",buildServiceCall(arguments)));
            bodyLines.add("\t\treturn XeResponseUtils.success();");
        }
        return String.join("\n", bodyLines);
    }

    @NotNull
    private String buildServiceCall(String arguments) {
        return String.format("%sService.%s(%s)",
                apiMethod.getCaller().getBuilder().buildCamelName(),
                apiMethod.getName(),
                arguments
        );
    }

    private String buildArguments() {
        List<String> paramNames = new ArrayList<>();
        this.apiMethod.getParameters().forEach((name, type) -> {
            paramNames.add(name);
        });

        this.apiMethod.getPathVars().forEach((name, type) -> {
            paramNames.add(name);
        });

        return String.join(", ", paramNames);
    }

    public String buildParams(){
        List<String> params = new ArrayList<>();
        buildBodyParams(params);
        buildPathParams(params);
        return String.join(", ", params);
    }

    private void buildBodyParams(List<String> params) {
        this.apiMethod.getParameters().forEach((name, type) -> {
            String param = String.format("@RequestBody %s %s", type.getSimpleName(), name);
            params.add(param);
        });
    }

    private void buildPathParams(List<String> params) {
        this.apiMethod.getPathVars().forEach((name, type) -> {
            String pathVar =  String.format("@PathVariable(\"%s\") %s %s)", name, type.getSimpleName(), name) ;
            params.add(pathVar);
        });
    }

    public String buildFullMethodString() {
        List<String> rs = new ArrayList<>();
        rs.add("\n");
        rs.add(new RequestAnnotation(apiMethod).toString());
        rs.add(buildMethodDeclaration());
        rs.add(buildBodyLines());
        rs.add("\t}");
        rs.add("\n");
        return String.join("\n", rs);
    }

    private String buildMethodDeclaration() {
        return String.format("\tpublic ResponseEntity<%s> %s (%s) {",
                buildReturnType(),
                this.apiMethod.getName(),
                this.buildParams());
    }

    public String buildReturnType(){
        return this.apiMethod.getReturnType() == null ? "Void" : this.apiMethod.getReturnType().getSimpleName();
    }
}
