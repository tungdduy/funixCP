package generator.app.renderers;

import generator.app.models.RestApiModel;
import generator.app.renderers.abstracts.AbstractRestRender;
import generator.urls.ApiMethod;
import generator.urls.UrlNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.lang.String.join;
import static util.StringUtil.toImportFormat;

public class RestApiFtl extends AbstractRestRender<RestApiModel> {

    @Override
    protected Class<?> getBuilderClass(UrlNode urlNode) {
        return urlNode.getBuilder().findControllerClass();
    }

    @Override
    protected void secondHandleSource(RestApiModel source) {
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
