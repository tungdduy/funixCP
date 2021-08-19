package generator.ts.xe.role;


import generator.abstracts.render.AbstractRender;

public class XeRoleTsRender extends AbstractRender<XeRoleTsModel> {

    @Override
    protected void handleModel(XeRoleTsModel model) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    public static void main(String[] args) {
        new XeRoleTsRender().singleRender();
    }
}
