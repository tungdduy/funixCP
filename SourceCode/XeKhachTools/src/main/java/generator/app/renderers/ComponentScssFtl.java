package generator.app.renderers;

import generator.app.models.ComponentScssModel;
import generator.app.renderers.abstracts.AbstractAppUrlTemplateRender;

public class ComponentScssFtl extends AbstractAppUrlTemplateRender<ComponentScssModel> {

    @Override
    protected void handleSource(ComponentScssModel source) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }
}
