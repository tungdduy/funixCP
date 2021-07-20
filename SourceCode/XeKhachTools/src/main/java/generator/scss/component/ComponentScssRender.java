package generator.scss.component;

import generator.abstracts.render.AbstractAppUrlRender;

public class ComponentScssRender extends AbstractAppUrlRender<ComponentScssModel> {

    @Override
    protected void handleModel(ComponentScssModel model) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }
}
