package generator.renders;

import architect.urls.UrlNode;
import generator.models.RouterComponentTsModel;
import generator.models.sub.RouterChildren;
import generator.renders.abstracts.AbstractAppUrlTemplateRender;
import util.AppUtil;

public class RouterComponentTsRender extends AbstractAppUrlTemplateRender<RouterComponentTsModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleModel(RouterComponentTsModel model) {
        UrlNode urlNode = model.getUrlNode();
        int level = urlNode.getBuilder().getLevel();
        model.setPathToFramework(AppUtil.getPathToFramework(level));
        model.setPathToI18n(AppUtil.getPathToI18n(level));
        model.setUrl(urlNode.getUrl());
        model.setCapitalizeName(urlNode.getBuilder().buildCapitalizeName());
        urlNode.getChildren().forEach(child -> {
            model.getChildren().add(new RouterChildren(child));
        });
    }
}
