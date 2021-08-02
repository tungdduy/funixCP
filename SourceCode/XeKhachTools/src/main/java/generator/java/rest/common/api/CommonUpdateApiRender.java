package generator.java.rest.common.api;

import data.entities.abstracts.AbstractEntity;
import generator.abstracts.render.AbstractRender;
import util.StringUtil;

import static util.ReflectionUtil.newInstancesOfAllChildren;

public class CommonUpdateApiRender extends AbstractRender<CommonUpdateApiModel> {


    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected void handleModel(CommonUpdateApiModel model) {
        String packageName = StringUtil.removeLastChar(AbstractEntity.class.getPackage().getName(), ".abstracts".length());
        model.setEntities(newInstancesOfAllChildren(AbstractEntity.class, packageName));
    }
}
