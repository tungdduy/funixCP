package generator.app.renderers;

import generator.app.models.ModuleTsModel;
import generator.app.renderers.abstracts.AbstractAppUrlTemplateRender;

public class ModuleTsFtl extends AbstractAppUrlTemplateRender<ModuleTsModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleSource(ModuleTsModel source) {
        source.setCapitalizeName(source.getUrlNode().getBuilder().buildCapitalizeName());
        source.setUrl(source.getUrlNode().getUrl());
        source.getUrlNode()
                .getChildren()
                .stream()
                .map(ModuleTsModel.Component::new)
                .forEach(source.getChildren()::add);
    }

}
