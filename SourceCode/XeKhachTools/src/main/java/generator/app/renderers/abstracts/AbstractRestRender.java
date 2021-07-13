package generator.app.renderers.abstracts;

import generator.app.builders.UrlNodeBuilder;
import generator.app.models.abstracts.AbstractRestModel;
import generator.urls.ApiMethod;
import generator.urls.UrlNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static net.timxekhach.utility.XeFileUtils.readAsString;
import static net.timxekhach.utility.XePredicateUtils.negate;
import static net.timxekhach.utility.XeStringUtils.fetchSeparatorContent;
import static net.timxekhach.utility.XeStringUtils.toImportFormat;

public abstract class AbstractRestRender<E extends AbstractRestModel> extends AbstractApiUrlTemplateRender<E> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    protected final List<String> fetchingImportContents = new ArrayList<>();

    abstract protected Class<?> getBuilderClass(UrlNode urlNode);
    abstract protected void secondHandleSource(E e);

    @Override
    protected void handleSource(E source) {
        UrlNode urlNode = source.getUrlNode();
        UrlNodeBuilder builder = urlNode.getBuilder();
        String currentContent = readAsString(source.buildRenderFilePath());
        source.setBodyContent(buildBodyContent(source, currentContent));
        source.setImportContent(buildImportContent(source, currentContent));
        source.setPackagePath(buildPackagePath(urlNode));
        source.setCapitalizeName(builder.buildCapitalizeName());
        this.secondHandleSource(source);
    }

    protected abstract String buildPackagePath(UrlNode urlNode);

    private String buildImportContent(E source, String currentContent) {
        List<String> importContents = new ArrayList<>();
        String currentImportContent = fetchSeparatorContent(source.getImportSeparator(), currentContent);
        if(currentImportContent.isEmpty()) {
            currentImportContent = initialImportContent(source.getUrlNode());
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
        return join("\n", importContents).trim();
    }

    abstract protected String initialImportContent(UrlNode urlNode);


    private String buildBodyContent(E source, String currentContent) {
        List<String> bodyContents = new ArrayList<>();
        String currentBodyContent = fetchSeparatorContent(source.getBodySeparator(), currentContent);
        if(!currentBodyContent.isEmpty()) {
            bodyContents.add(currentBodyContent);
        }
        List<ApiMethod> methodsToBuilder = filterMethodToBuilder(source);
        String newBodyContent = buildMethodContents(methodsToBuilder);
        if(!newBodyContent.isEmpty()) {
            bodyContents.add(newBodyContent);
        }
        return "\t".concat(join("\n", bodyContents).trim());
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
