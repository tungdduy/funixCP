package generator.renders;

import architect.urls.ApiMethod;
import architect.urls.UrlNode;
import generator.models.RestServiceModel;
import generator.renders.abstracts.AbstractRestRender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.join;
import static util.StringUtil.toImportFormat;

public class RestServiceRender extends AbstractRestRender<RestServiceModel> {
    @Override
    protected Class<?> getBuilderClass(UrlNode urlNode) {
        return urlNode.getBuilder().findServiceClass();
    }

    @Override
    protected void secondHandleModel(RestServiceModel restServiceModel) {
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