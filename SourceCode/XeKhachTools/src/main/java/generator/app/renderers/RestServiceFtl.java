package generator.app.renderers;

import generator.app.models.RestServiceModel;
import generator.app.renderers.abstracts.AbstractRestRender;
import generator.urls.ApiMethod;
import generator.urls.UrlNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.join;
import static util.StringUtil.toImportFormat;

public class RestServiceFtl extends AbstractRestRender<RestServiceModel> {
    @Override
    protected Class<?> getBuilderClass(UrlNode urlNode) {
        return urlNode.getBuilder().findServiceClass();
    }

    @Override
    protected void secondHandleSource(RestServiceModel restServiceModel) {

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