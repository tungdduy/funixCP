package generator.renders;


import generator.models.XeRoleTsModel;
import generator.renders.abstracts.AbstractTemplateRender;

public class XeRoleTsRender extends AbstractTemplateRender<XeRoleTsModel> {

    @Override
    protected void handleModel(XeRoleTsModel model) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

}
