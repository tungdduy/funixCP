package generator.abstracts.render;

import architect.urls.ApiMethod;
import architect.urls.UrlNode;
import com.sun.istack.internal.NotNull;
import generator.abstracts.models.AbstractRestModel;
import generator.builders.UrlNodeBuilder;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.join;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
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
        model.setCapName(builder.buildCapName());
        this.secondHandleModel(model);
    }

    protected abstract String buildPackagePath(UrlNode urlNode);

    private void buildImportContent(E model) {
        String fetchingImportContent = fetchingImportContents.stream()
                .filter(s -> !s.startsWith("import java.lang"))
                .distinct()
                .collect(joining("\n"));
        model.separator("import").unique(fetchingImportContent);
    }

    private void buildBodyContent(E model) {
        List<ApiMethod> methodsToBuilder = filterMethodToBuilder(model);
        String newBodyContent = buildMethodContents(methodsToBuilder);
        model.separator("body").append(newBodyContent);
    }

    @NotNull
    private List<ApiMethod> filterMethodToBuilder(E source) {
        try {

            String sourceText = FileUtil.readAsString(source.buildRenderFilePath());
            Class<?> clazz = getBuilderClass(source.getUrlNode());
            return source.getUrlNode().getMethods().stream()
                    .filter(method -> !sourceText.contains(method.getBuilder().buildCamelName()))
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
