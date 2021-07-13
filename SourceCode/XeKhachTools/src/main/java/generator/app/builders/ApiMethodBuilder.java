package generator.app.builders;

import com.sun.istack.internal.NotNull;
import generator.urls.ApiMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.lang.String.join;
import static net.timxekhach.utility.XeStringUtils.toCamel;
import static net.timxekhach.utility.XeStringUtils.toKey;

public class ApiMethodBuilder {

    private final ApiMethod apiMethod;
    private final ApiMethodAnnotationBuilder apiMethodAnnotationBuilder;

    @SuppressWarnings("rawtypes")
    public Class[] getAllParamTypes(){
        List<Class> paramClasses = new ArrayList<>();
        paramClasses.addAll(apiMethod.getParameters().values());
        paramClasses.addAll(apiMethod.getPathVars().values());
        return paramClasses.toArray(new Class[0]);
    }

    public ApiMethodBuilder(ApiMethod apiMethod) {
        this.apiMethod = apiMethod;
        this.apiMethodAnnotationBuilder = new ApiMethodAnnotationBuilder(apiMethod);
    }

    public String buildKey() {
        return toKey(this.apiMethod.getUrl());
    }

    public String buildUrlChain() {
        return String.format("%s/%s",
                apiMethod.getCaller().getBuilder().buildUrlChain(),
                apiMethod.getUrl());
    }

    public String buildCamelName() {
        return toCamel(this.apiMethod.getUrl());
    }

    public String buildGenericReturnType(){
        return this.apiMethod.getReturnType() == null ? "Void" : this.apiMethod.getReturnType().getSimpleName();
    }

    public String buildReturnType(){
        return this.apiMethod.getReturnType() == null ? "void" : this.apiMethod.getReturnType().getSimpleName();
    }

    @NotNull
    private String buildServiceCall(String arguments) {
        return format("%sService.%s(%s)",
                apiMethod.getCaller().getBuilder().buildCamelName(),
                buildCamelName(),
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

        return join(", ", paramNames);
    }


    // ------------- start CONTROLLER building____________

    public List<String> allControllerImportClasses() {
        Set<String> result = new HashSet<>(apiMethodAnnotationBuilder.allImportClasses());
        apiMethod.getParameters().forEach((name, param) -> {
            result.add(param.getName());
        });
        apiMethod.getPathVars().forEach((name, type) -> {
            result.add(type.getName());
        });
        return new ArrayList<>(result);
    }

    public String buildControllerParams(){
        List<String> params = new ArrayList<>();
        buildControllerBodyParams(params);
        buildControllerPathParams(params);
        return join(", ", params);
    }

    private void buildControllerBodyParams(List<String> params) {
        this.apiMethod.getParameters().forEach((name, type) -> {
            String oriType = type.getSimpleName();
            String convertedType = oriType.equals("Map") ? "Map<String, String>" : oriType;
            String param = format("@RequestBody %s %s", convertedType, name);
            params.add(param);
        });
    }

    private void buildControllerPathParams(List<String> params) {
        this.apiMethod.getPathVars().forEach((name, type) -> {
            String pathVar =  format("@PathVariable(\"%s\") %s %s", name, type.getSimpleName(), name) ;
            params.add(pathVar);
        });
    }

    public String buildControllerString() {
        List<String> rs = new ArrayList<>();
        rs.add(new ApiMethodAnnotationBuilder(apiMethod).buildFullString());
        rs.add(buildControllerDeclaration());
        rs.add(buildControllerBody());
        rs.add("\t}");
        return join("\n", rs);
    }

    private String buildControllerDeclaration() {
        return format("\tpublic ResponseEntity<%s> %s (%s) {",
                buildGenericReturnType(),
                this.buildCamelName(),
                this.buildControllerParams());
    }

    public String buildControllerBody() {
        List<String> bodyLines = new ArrayList<>();

        String arguments = buildArguments();

        if(apiMethod.getReturnType() != null) {
            bodyLines.add(format("\t\treturn success(%s);",  buildServiceCall(arguments)));
        } else {
            bodyLines.add(format("\t\t%s;",buildServiceCall(arguments)));
            bodyLines.add("\t\treturn success();");
        }
        return join("\n", bodyLines);
    }

    //_______________ end CONTROLLER building-------------------------------

    //--------------- start Service building_________________________
    public String buildServiceString() {
        List<String> rs = new ArrayList<>();
        rs.add(buildServiceDeclaration());
        rs.add(buildServiceBody());
        rs.add("\t}");
        return join("\n", rs);
    }

    private String buildServiceBody() {
        List<String> body = new ArrayList<> ();
        body.add(String.format("\t\t// TODO: service %s method", buildCamelName()));
        if (!buildReturnType().equals("void")) {
            body.add("return null;");
        }
        return join("\n\t\t", body);
    }

    private String buildServiceDeclaration() {
        return format("\tpublic %s %s (%s) {",
                buildReturnType(),
                this.buildCamelName(),
                this.buildServiceParams());
    }

    public String buildServiceParams(){
        List<String> params = new ArrayList<>();
        buildServiceBodyParams(params);
        buildServicePathParams(params);
        return join(", ", params);
    }

    private void buildServiceBodyParams(List<String> params) {
        this.apiMethod.getParameters().forEach((name, type) -> {
            String oriType = type.getSimpleName();
            String convertedType = oriType.equals("Map") ? "Map<String, String>" : oriType;
            String param = format("%s %s", convertedType, name);
            params.add(param);
        });
    }

    private void buildServicePathParams(List<String> params) {
        this.apiMethod.getPathVars().forEach((name, type) -> {
            String pathVar =  format("%s %s", type.getSimpleName(), name) ;
            params.add(pathVar);
        });
    }

    public List<String> allServiceImportClasses() {
        Set<String> result = new HashSet<>();
        apiMethod.getParameters().forEach((name, param) -> {
            result.add(param.getName());
        });
        apiMethod.getPathVars().forEach((name, type) -> {
            result.add(type.getName());
        });
        return new ArrayList<>(result);
    }


}
