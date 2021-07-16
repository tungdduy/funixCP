package generator.renders;


import generator.models.XeAuthTsModel;
import generator.renders.abstracts.AbstractRender;

public class XeAuthTsRender extends AbstractRender<XeAuthTsModel> {

    @Override
    protected void handleModel(XeAuthTsModel model) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }


}
