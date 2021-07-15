package generator.renders;

import generator.models.ComponentHtmlModel;
import generator.renders.abstracts.AbstractAppUrlTemplateRender;

public class ComponentHtmlRender extends AbstractAppUrlTemplateRender<ComponentHtmlModel> {
    @Override
    protected void handleModel(ComponentHtmlModel model) {
        model.setName(model.getUrlNode().getBuilder().buildCapitalizeName() + " worked!");
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }
}
