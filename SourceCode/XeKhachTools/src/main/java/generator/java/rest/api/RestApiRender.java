package generator.java.rest.api;

import architect.urls.ApiMethod;
import architect.urls.UrlNode;
import generator.abstracts.render.AbstractRestRender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.lang.String.join;
import static util.StringUtil.toImportFormat;

public class RestApiRender extends AbstractRestRender<RestApiModel> {

    @Override
    protected Class<?> getBuilderClass(UrlNode urlNode) {
        return urlNode.getBuilder().findControllerClass();
    }

    @Override
    protected void secondHandleModel(RestApiModel source) {
        source.setUrl(source.getUrlNode().getUrl());
        source.setCamelName(source.getUrlNode().getBuilder().buildCamelName());
    }

    @Override
    protected String buildPackagePath(UrlNode urlNode) {
        return urlNode.getBuilder().buildControllerPackagePath();
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
