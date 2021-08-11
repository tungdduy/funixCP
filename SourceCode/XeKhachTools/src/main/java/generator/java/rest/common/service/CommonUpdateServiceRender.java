package generator.java.rest.common.service;

import data.entities.abstracts.AbstractEntity;
import generator.abstracts.render.AbstractRender;
import util.StringUtil;

import static util.ReflectionUtil.newInstancesOfAllChildren;

public class CommonUpdateServiceRender extends AbstractRender<CommonUpdateServiceModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected void handleModel(CommonUpdateServiceModel model) {
        String packageName = StringUtil.removeLastChar(AbstractEntity.class.getPackage().getName(), ".abstracts".length());
        model.setEntities(newInstancesOfAllChildren(AbstractEntity.class, packageName));
    }

    public static void main(String[] args) {
        new CommonUpdateServiceRender().executeRenders();
    }
}
