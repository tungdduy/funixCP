package generator.java.data.mapped.angulars;

import generator.abstracts.render.AbstractEntityRender;
import generator.java.data.mapped.EntityMappedModel;
import generator.java.data.mapped.EntityMappedRender;

import java.util.ArrayList;
import java.util.List;

public class EntityRender extends EntityMappedRender<EntityModel> {

    @Override
    protected void handleModel(EntityMappedModel model) {
        super.handleModel(model);
        List<String> allPkIdsChain = new ArrayList<>();
        model.getEntity().getAllPkIdsChain(model.getEntity().getPrimaryKeyEntities(), null, allPkIdsChain, "?.");
        model.setPkIdChains(allPkIdsChain);
    }

    protected void handleModel(EntityModel model) {
        super.handleModel(model);

    }

    public static void main(String[] args) {
        new EntityRender();
        AbstractEntityRender.renderWithParent();
    }
}
