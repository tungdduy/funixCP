package generator.renders;


import generator.models.XeAuthTsModel;
import generator.renders.abstracts.AbstractTemplateRender;

public class XeAuthTsRender extends AbstractTemplateRender<XeAuthTsModel> {

    @Override
    protected void handleModel(XeAuthTsModel model) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }


}
