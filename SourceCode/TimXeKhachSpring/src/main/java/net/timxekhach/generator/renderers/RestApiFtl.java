package net.timxekhach.generator.renderers;

import lombok.RequiredArgsConstructor;
import net.timxekhach.generator.abstracts.rest.AbstractRestBuilder;
import net.timxekhach.generator.sources.RestApiSource;
import net.timxekhach.security.model.ApiMethod;
import net.timxekhach.security.model.UrlNode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.lang.String.join;
import static net.timxekhach.utility.XeStringUtils.toImportFormat;

public class RestApiFtl extends AbstractRestBuilder<RestApiSource> {

    @Override
    protected Class<?> getBuilderClass(UrlNode urlNode) {
        return urlNode.getBuilder().findControllerClass();
    }

    @Override
    protected void secondHandleSource(RestApiSource source) {
        source.setUrl(source.getUrlNode().getUrl());
        source.setCamelName(source.getUrlNode().getBuilder().buildCamelName());
    }

    @Override
    protected String buildPackagePath(UrlNode urlNode) {
        return urlNode.getBuilder().buildControllerPackagePath();
    }

    @Override
    protected String initialImportContent(UrlNode urlNode) {
        return join("\n", toImportFormat(
                RequiredArgsConstructor.class.getName(),
                "org.springframework.web.bind.annotation.*",
                urlNode.getBuilder().buildFullServiceClassName(),
                "static net.timxekhach.utility.XeResponseUtils.success",
                ResponseEntity.class.getName()
        ));
    }

    @Override
    protected String buildMethodString(ApiMethod method) {
        return method.getBuilder().buildControllerString();
    }

    @Override
    protected List<String> allImportClasses(ApiMethod method) {
        return method.getBuilder().allControllerImportClasses();
    }

}
