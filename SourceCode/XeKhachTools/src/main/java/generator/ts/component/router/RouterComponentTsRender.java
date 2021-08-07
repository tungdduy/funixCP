package generator.ts.component.router;

import architect.urls.UrlNode;
import generator.abstracts.render.AbstractAppUrlRender;

public class RouterComponentTsRender extends AbstractAppUrlRender<RouterComponentTsModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleModel(RouterComponentTsModel model) {
        UrlNode urlNode = model.getUrlNode();
        model.setUrl(urlNode.getUrl());
        model.setCapName(urlNode.getBuilder().buildCapName());
    }
}
