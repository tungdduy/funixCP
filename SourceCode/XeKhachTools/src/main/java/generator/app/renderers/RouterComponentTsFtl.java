package generator.app.renderers;

import generator.app.models.RouterComponentTsModel;
import generator.app.renderers.abstracts.AbstractAppUrlTemplateRender;
import generator.urls.UrlNode;
import util.AppUtil;

public class RouterComponentTsFtl extends AbstractAppUrlTemplateRender<RouterComponentTsModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleSource(RouterComponentTsModel source) {
        UrlNode urlNode = source.getUrlNode();
        int level = urlNode.getBuilder().getLevel();
        source.setPathToFramework(AppUtil.getPathToFramework(level));
        source.setPathToI18n(AppUtil.getPathToI18n(level));
        source.setUrl(urlNode.getUrl());
        source.setCapitalizeName(urlNode.getBuilder().buildCapitalizeName());
        urlNode.getChildren().forEach(child -> {
            source.getChildren().add(new RouterComponentTsModel.Children(child));
        });
    }
}
