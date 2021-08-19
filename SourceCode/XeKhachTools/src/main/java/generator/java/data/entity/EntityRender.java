package generator.java.data.entity;

import generator.abstracts.render.AbstractEntityRender;

public class EntityRender extends AbstractEntityRender<EntityModel> {

    @Override
    protected void handleModel(EntityModel model) {
        model.hasProfileImage = model.getEntity().hasProfileImage();
        model.getEntity().getPrimaryKeyEntities().forEach(pkEntity -> {
            this.updateConstructorParams(model, pkEntity);
        });
    }

    public static void main(String[] args) {
        new EntityRender().singleRender();
    }

}
