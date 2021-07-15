package generator.renders;

import generator.models.RoutingModuleTsModel;
import generator.renders.abstracts.AbstractAppUrlTemplateRender;
import util.AppUtil;

public class RoutingModuleTsRender extends AbstractAppUrlTemplateRender<RoutingModuleTsModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleModel(RoutingModuleTsModel model) {
        int level = model.getUrlNode().getBuilder().getLevel();
        model.setPathToFramework(AppUtil.getPathToFramework(level));
        model.setUrlKeyChain(model.getUrlNode().getBuilder().buildKeyChain());
        model.setCapitalizeName(model.getUrlNode().getBuilder().buildCapitalizeName());
    }
}
