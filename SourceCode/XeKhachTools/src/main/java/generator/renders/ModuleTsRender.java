package generator.renders;

import generator.models.ModuleTsModel;
import generator.models.sub.Component;
import generator.renders.abstracts.AbstractAppUrlTemplateRender;

public class ModuleTsRender extends AbstractAppUrlTemplateRender<ModuleTsModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleModel(ModuleTsModel model) {
        model.setCapitalizeName(model.getUrlNode().getBuilder().buildCapitalizeName());
        model.setUrl(model.getUrlNode().getUrl());
        model.getUrlNode()
                .getChildren()
                .stream()
                .map(Component::new)
                .forEach(model.getChildren()::add);
    }

}
