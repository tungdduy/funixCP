package generator.renders;

import generator.models.ComponentTsModel;
import generator.renders.abstracts.AbstractAppUrlTemplateRender;
import architect.urls.UrlNode;
import util.AppUtil;

public class ComponentTsRender extends AbstractAppUrlTemplateRender<ComponentTsModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }

    @Override
    protected void handleModel(ComponentTsModel model) {
        UrlNode urlNode = model.getUrlNode();
        int level = urlNode.getBuilder().getLevel();
        model.setPathToFramework(AppUtil.getPathToFramework(level));
        model.setPathToAbstract(AppUtil.getPathToAbstract(level));
        model.setPathToI18n(AppUtil.getPathToI18n(level));
        model.setUrl(urlNode.getUrl());
        model.setComponentName(urlNode.getBuilder().buildComponentName());
    }
}
