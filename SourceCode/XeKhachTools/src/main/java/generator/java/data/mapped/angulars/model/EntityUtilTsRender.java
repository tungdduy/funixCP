package generator.java.data.mapped.angulars.model;

import generator.abstracts.render.AbstractEntityRender;
import generator.java.data.mapped.EntityMappedModel;
import generator.java.data.mapped.EntityMappedRender;

import java.util.ArrayList;
import java.util.List;

public class EntityUtilTsRender extends AbstractEntityRender<EntityUtilTsModel> {

    @Override
    protected void handleModel(EntityUtilTsModel model) {
        model.update();
        List<String> allPkIdsChain = new ArrayList<>();
        model.getEntity().getAllPkIdsChain(model.getEntity().getPrimaryKeyEntities(), null, allPkIdsChain, "?.");
        model.setPkIdChains(allPkIdsChain);
    }

    @Override
    public void runBeforeRender() {
        List<EntityUtilTsModel> allModels = new ArrayList<>(this.getModelFiles());
        this.getModelFiles().clear();
        EntityUtilTsModel model = new EntityUtilTsModel();
        model.setAllModels(allModels);
        this.getModelFiles().add(model);
    }

    public static void main(String[] args) {
        new EntityUtilTsRender();
        AbstractEntityRender.renderWithParent();
    }
}
