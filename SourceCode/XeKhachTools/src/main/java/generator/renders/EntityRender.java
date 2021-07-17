package generator.renders;

import generator.models.EntityModel;
import generator.renders.abstracts.AbstractEntityRender;

public class EntityRender extends AbstractEntityRender<EntityModel> {

    @Override
    protected void handleModel(EntityModel model) {
        model.updateBodyImportContent();
        model.getEntity().getPrimaryKeyEntities().forEach(pkEntity -> {
            this.updateConstructorParams(model, pkEntity);
        });
    }

}
