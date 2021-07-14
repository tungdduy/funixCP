package generator.app.renderers;

import generator.app.models.ComponentTsModel;
import generator.app.renderers.abstracts.AbstractAppUrlTemplateRender;
import generator.urls.UrlNode;
import util.AppUtil;

public class ComponentTsFtl extends AbstractAppUrlTemplateRender<ComponentTsModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }

    @Override
    protected void handleSource(ComponentTsModel source) {
        UrlNode urlNode = source.getUrlNode();
        int level = urlNode.getBuilder().getLevel();
        source.setPathToFramework(AppUtil.getPathToFramework(level));
        source.setPathToAbstract(AppUtil.getPathToAbstract(level));
        source.setPathToI18n(AppUtil.getPathToI18n(level));
        source.setUrl(urlNode.getUrl());
        source.setComponentName(urlNode.getBuilder().buildComponentName());
    }
}
