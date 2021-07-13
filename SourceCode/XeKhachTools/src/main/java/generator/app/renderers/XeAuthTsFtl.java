package generator.app.renderers;


import generator.app.models.XeAuthTsModel;
import generator.app.renderers.abstracts.AbstractTemplateRender;

public class XeAuthTsFtl extends AbstractTemplateRender<XeAuthTsModel> {

    @Override
    protected void handleSource(XeAuthTsModel source) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }


}
