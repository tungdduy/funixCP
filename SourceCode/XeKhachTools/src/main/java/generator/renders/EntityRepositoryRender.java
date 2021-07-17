package generator.renders;

import generator.models.EntityModel;
import generator.models.EntityRepositoryModel;
import generator.renders.abstracts.AbstractEntityRender;

public class EntityRepositoryRender extends AbstractEntityRender<EntityRepositoryModel> {

    @Override
    protected void handleModel(EntityRepositoryModel model) {
        model.updateBodyImportContent();
    }

}
