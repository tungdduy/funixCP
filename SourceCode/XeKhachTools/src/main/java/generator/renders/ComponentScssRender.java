package generator.renders;

import generator.models.ComponentScssModel;
import generator.renders.abstracts.AbstractAppUrlRender;

public class ComponentScssRender extends AbstractAppUrlRender<ComponentScssModel> {

    @Override
    protected void handleModel(ComponentScssModel model) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }
}
