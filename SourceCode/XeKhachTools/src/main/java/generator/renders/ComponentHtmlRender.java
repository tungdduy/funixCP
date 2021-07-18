package generator.renders;

import generator.models.ComponentHtmlModel;
import generator.renders.abstracts.AbstractAppUrlRender;

public class ComponentHtmlRender extends AbstractAppUrlRender<ComponentHtmlModel> {
    @Override
    protected void handleModel(ComponentHtmlModel model) {
        model.setName(model.getUrlNode().getBuilder().buildCapitalizeName());
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }
}
