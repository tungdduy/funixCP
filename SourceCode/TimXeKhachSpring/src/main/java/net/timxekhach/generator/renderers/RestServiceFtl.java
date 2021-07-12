package net.timxekhach.generator.renderers;

import lombok.RequiredArgsConstructor;
import net.timxekhach.generator.abstracts.rest.AbstractRestBuilder;
import net.timxekhach.generator.sources.RestServiceSource;
import net.timxekhach.security.model.ApiMethod;
import net.timxekhach.security.model.UrlNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.stream.Collectors.joining;
import static net.timxekhach.utility.XeStringUtils.toImportFormat;

public class RestServiceFtl extends AbstractRestBuilder<RestServiceSource> {
    @Override
    protected Class<?> getBuilderClass(UrlNode urlNode) {
        return urlNode.getBuilder().findServiceClass();
    }

    @Override
    protected void secondHandleSource(RestServiceSource restServiceSource) {

    }

    @Override
    protected String buildPackagePath(UrlNode urlNode) {
        return urlNode.getBuilder().buildServicePackagePath();
    }

    @Override
    protected String initialImportContent(UrlNode urlNode) {
        return join("\n", toImportFormat(
                RequiredArgsConstructor.class.getName(),
                Transactional.class.getName(),
                Service.class.getName()
        ));
    }

    @Override
    protected String buildMethodString(ApiMethod method) {
        return method.getBuilder().buildServiceString();
    }

    @Override
    protected List<String> allImportClasses(ApiMethod method) {
        return method.getBuilder().allServiceImportClasses();
    }
}