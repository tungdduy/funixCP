package generator.java.data.entity;

import generator.abstracts.render.AbstractEntityRender;

public class EntityRender extends AbstractEntityRender<EntityModel> {

    @Override
    protected void handleModel(EntityModel model) {
        model.getEntity().getPrimaryKeyEntities().forEach(pkEntity -> {
            this.updateConstructorParams(model, pkEntity);
        });
    }

}
