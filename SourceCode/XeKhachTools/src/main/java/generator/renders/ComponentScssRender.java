package generator.renders;

import generator.models.ComponentScssModel;
import generator.renders.abstracts.AbstractAppUrlTemplateRender;

public class ComponentScssRender extends AbstractAppUrlTemplateRender<ComponentScssModel> {

    @Override
    protected void handleModel(ComponentScssModel model) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }
}
