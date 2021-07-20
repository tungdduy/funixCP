package generator.renders;


import generator.models.XeRoleTsModel;
import generator.renders.abstracts.AbstractRender;

public class XeRoleTsRender extends AbstractRender<XeRoleTsModel> {

    @Override
    protected void handleModel(XeRoleTsModel model) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

}
