package net.timxekhach.generator.renderers;

import lombok.RequiredArgsConstructor;
import net.timxekhach.generator.abstracts.AbstractApiUrlTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.generator.builders.UrlNodeBuilder;
import net.timxekhach.generator.sources.RestApiSource;
import net.timxekhach.generator.sources.RestServiceSource;
import net.timxekhach.security.model.ApiMethod;
import net.timxekhach.security.model.UrlNode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static net.timxekhach.generator.GeneratorCenter.API_OPERATION_REST_SERVICE_ROOT;
import static net.timxekhach.utility.XeFileUtils.readAsString;
import static net.timxekhach.utility.XePredicateUtils.negate;
import static net.timxekhach.utility.XeStringUtils.fetchContentOrEmpty;
import static net.timxekhach.utility.XeStringUtils.toImportFormat;

public class RestServiceFtl extends AbstractApiUrlTemplateBuilder<RestServiceSource> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected void handleSource(RestServiceSource source) {
        UrlNode urlNode = source.getUrlNode();
        UrlNodeBuilder builder = urlNode.getBuilder();
        String currentContent = readAsString(source.buildRenderFilePath());
        source.setBodyContent(buildBodyContent(source, currentContent));
        source.setImportContent(buildImportContent(source, currentContent));
        source.setPackagePath(builder.buildServicePackagePath());
        source.setCapitalizeName(builder.buildCapitalizeName());
    }

    private final List<String> fetchingImportContents = new ArrayList<>();

    private String buildImportContent(RestServiceSource source, String currentContent) {
        String currentImportContent = fetchContentOrEmpty(source.getImportSeparator(), currentContent);
        if(currentImportContent.isEmpty()) {
            currentImportContent = initialImportContent(source.getUrlNode());
        }
        String fetchingImportContent = fetchingImportContents.stream()
                .filter(negate(currentImportContent::contains))
                .filter(s -> !s.startsWith("import java.lang"))
                .distinct()
                .collect(joining("\n"));

        return format("%s%n%s", currentImportContent, fetchingImportContent);
    }

    private String initialImportContent(UrlNode urlNode) {
        return join("\n", toImportFormat(
                RequiredArgsConstructor.class.getName(),
                Transactional.class.getName(),
                Service.class.getName()
        ));
    }

    private String buildBodyContent(RestServiceSource source, String currentContent) {
        String currentBodyContent = fetchContentOrEmpty(source.getBodySeparator(), currentContent);
        List<ApiMethod> methodsToBuilder = filterMethodToBuilder(source);
        String newBodyContent = buildMethodContents(methodsToBuilder);
        return format("%s%n%s", currentBodyContent, newBodyContent);
    }

    @NotNull
    private List<ApiMethod> filterMethodToBuilder(RestServiceSource source) {
        try {
            Class<?> serviceClass = source.getUrlNode().getBuilder().findServiceClass();
            return source.getUrlNode().getMethods().stream()
                    .filter(method -> {
                        try {
                            serviceClass.getMethod(method.getUrl(), method.getBuilder().getAllParamTypes());
                            return false;
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        return true;
                    })
                    .collect(toList());
        } catch (Exception ex) {
            logger.debug("Rest Service not exist. will create!");
        }
        return source.getUrlNode().getMethods();
    }

    private String buildMethodContents(List<ApiMethod> methods) {
        return methods.stream()
                .map(this::buildMethodContent)
                .collect(joining("\n\n"));
    }

    private String buildMethodContent(ApiMethod method) {
        this.fetchingImportContents.addAll(toImportFormat(method.getBuilder().allImportClasses()));
        return method.getBuilder().buildServiceString();
    }

}