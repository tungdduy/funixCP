package generator.app.renderers;

import generator.app.models.ComponentHtmlModel;
import generator.app.renderers.abstracts.AbstractAppUrlTemplateRender;

public class ComponentHtmlFtl extends AbstractAppUrlTemplateRender<ComponentHtmlModel> {
    @Override
    protected void handleSource(ComponentHtmlModel source) {
        source.setName(source.getUrlNode().getBuilder().buildCapitalizeName() + " worked!");
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }
}
