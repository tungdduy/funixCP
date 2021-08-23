package generator.java.data.mapped.angulars.entity;

import generator.abstracts.render.AbstractEntityRender;
import generator.java.data.mapped.EntityMappedModel;
import generator.java.data.mapped.EntityMappedRender;

import java.util.ArrayList;
import java.util.List;

public class EntityRender extends AbstractEntityRender<EntityModel> {

    @Override
    protected void handleModel(EntityModel model) {
        model.update();
        List<String> allPkIdsChain = new ArrayList<>();
        model.getEntity().getAllPkIdsChain(model.getEntity().getPrimaryKeyEntities(), null, allPkIdsChain, "?.");
        model.setPkIdChains(allPkIdsChain);
    }

    public static void main(String[] args) {
        new EntityRender();
        AbstractEntityRender.renderWithParent();
    }
}
