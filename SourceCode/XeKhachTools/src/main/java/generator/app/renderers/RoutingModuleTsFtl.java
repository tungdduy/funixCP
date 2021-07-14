package generator.app.renderers;

import generator.app.models.RoutingModuleTsModel;
import generator.app.renderers.abstracts.AbstractAppUrlTemplateRender;
import util.AppUtil;

public class RoutingModuleTsFtl extends AbstractAppUrlTemplateRender<RoutingModuleTsModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleSource(RoutingModuleTsModel source) {
        int level = source.getUrlNode().getBuilder().getLevel();
        source.setPathToFramework(AppUtil.getPathToFramework(level));
        source.setUrlKeyChain(source.getUrlNode().getBuilder().buildKeyChain());
        source.setCapitalizeName(source.getUrlNode().getBuilder().buildCapitalizeName());
    }
}
