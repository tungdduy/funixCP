package generator.renders.abstracts;

import architect.urls.ApiMethod;
import architect.urls.UrlNode;
import com.sun.istack.internal.NotNull;
import generator.builders.UrlNodeBuilder;
import generator.models.abstracts.AbstractRestModel;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static util.PredicateUtil.negate;
import static util.StringUtil.toImportFormat;

public abstract class AbstractRestRender<E extends AbstractRestModel> extends AbstractApiUrlRender<E> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    protected final List<String> fetchingImportContents = new ArrayList<>();

    abstract protected Class<?> getBuilderClass(UrlNode urlNode);
    abstract protected void secondHandleModel(E e);

    @Override
    protected void handleModel(E model) {
        UrlNode urlNode = model.getUrlNode();
        UrlNodeBuilder builder = urlNode.getBuilder();
        buildBodyContent(model);
        buildImportContent(model);
        model.setPackagePath(buildPackagePath(urlNode));
        model.setCapitalizeName(builder.buildCapitalizeName());
        this.secondHandleModel(model);
    }

    protected abstract String buildPackagePath(UrlNode urlNode);

    private void buildImportContent(E model) {
        List<String> importContents = new ArrayList<>();
        String currentImportContent = model.getImportContent();
        if(currentImportContent.isEmpty()) {
            currentImportContent = initialImportContent(model.getUrlNode());
        }
        String fetchingImportContent = fetchingImportContents.stream()
                .filter(negate(currentImportContent::contains))
                .filter(s -> !s.startsWith("import java.lang"))
                .distinct()
                .collect(joining("\n"));
        importContents.add(currentImportContent);
        if (!fetchingImportContent.isEmpty()) {
            importContents.add(fetchingImportContent);
        }
        model.setImportContent(join("\n", importContents).trim());
    }

    abstract protected String initialImportContent(UrlNode urlNode);

    private void buildBodyContent(E model) {
        List<String> bodyContents = new ArrayList<>();
        String currentBodyContent = model.getBodyContent();
        if(!currentBodyContent.isEmpty()) {
            bodyContents.add(currentBodyContent);
        }
        List<ApiMethod> methodsToBuilder = filterMethodToBuilder(model);
        String newBodyContent = buildMethodContents(methodsToBuilder);
        if(!newBodyContent.isEmpty()) {
            bodyContents.add(newBodyContent);
        }
        model.setBodyContent("\t".concat(join("\n", bodyContents).trim()));
    }

    @NotNull
    private List<ApiMethod> filterMethodToBuilder(E source) {
        try {
            Class<?> clazz = getBuilderClass(source.getUrlNode());
            return source.getUrlNode().getMethods().stream()
                    .filter(method -> {
                        try {
                            clazz.getMethod(method.getBuilder().buildCamelName(), method.getBuilder().getAllParamTypes());
                            return false;
                        } catch (NoSuchMethodException e) {
                            logger.debug(format("method %s not found", method.getBuilder().buildCamelName()));
                        }
                        return true;
                    })
                    .collect(toList());
        } catch (Exception ex) {
            logger.debug("Rest class not exist. will create!");
        }
        return source.getUrlNode().getMethods();
    }

    private String buildMethodContents(List<ApiMethod> methods) {
        return methods.stream()
                .map(this::buildMethodContent)
                .collect(joining("\n\n"));
    }

    private String buildMethodContent(ApiMethod method) {
        this.fetchingImportContents.addAll(toImportFormat(allImportClasses(method)));
        return buildMethodString(method);
    }

    protected abstract String buildMethodString(ApiMethod method);

    protected abstract List<String> allImportClasses(ApiMethod method);
}
