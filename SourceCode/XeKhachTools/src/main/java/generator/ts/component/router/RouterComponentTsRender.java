package generator.ts.component.router;

import architect.urls.UrlNode;
import generator.abstracts.render.AbstractAppUrlRender;
import util.AppUtil;

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
        model.setCapitalizeName(urlNode.getBuilder().buildCapitalizeName());
    }
}
