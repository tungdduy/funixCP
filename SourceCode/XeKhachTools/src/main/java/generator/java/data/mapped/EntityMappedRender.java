package generator.java.data.mapped;

import generator.abstracts.render.AbstractEntityRender;

@SuppressWarnings("unused")
public class EntityMappedRender extends AbstractEntityRender<EntityMappedModel> {

    public static void main(String[] args) {
        new EntityMappedRender().singleRender();
    }

    @Override
    protected void handleModel(EntityMappedModel model) {
        model.update();
    }
}
