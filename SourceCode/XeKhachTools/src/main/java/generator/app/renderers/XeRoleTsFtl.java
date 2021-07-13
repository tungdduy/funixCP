package generator.app.renderers;


import generator.app.models.XeRoleTsModel;
import generator.app.renderers.abstracts.AbstractTemplateRender;

public class XeRoleTsFtl extends AbstractTemplateRender<XeRoleTsModel> {

    @Override
    protected void handleSource(XeRoleTsModel source) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

}
