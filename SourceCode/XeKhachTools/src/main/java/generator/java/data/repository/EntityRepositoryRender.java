package generator.java.data.repository;

import data.entities.abstracts.AbstractEntity;
import generator.abstracts.render.AbstractEntityRender;

import java.util.ArrayList;
import java.util.List;

public class EntityRepositoryRender extends AbstractEntityRender<EntityRepositoryModel> {

    @Override
    protected void handleModel(EntityRepositoryModel model) {
        updateFindByPks(model);
    }

    private void updateFindByPks(EntityRepositoryModel model) {
        List<String> ids = new ArrayList<>(model.getEntity().getPrimaryKeyIdNames());
        int size = ids.size();
        List<FindMethod> byPkMethods = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            FindMethod findMethod = new FindMethod(ids.get(i));
            findMethod.addParam(ids.get(i));
            byPkMethods.add(findMethod);
            if (i < size - 1) {
                for (int j = i + 1; j < size; j++) {
                    findMethod = findMethod.appendThenNew(ids.get(j));
                    byPkMethods.add(findMethod);
                }
            }
        }
        model.setFindByPks(byPkMethods);
    }



    @Override
    public void runBeforeRender() {
        this.getModelFiles().forEach(model -> {
            List<CapCamel> whoCountMe = AbstractEntity.whoCountMe.get(model.getEntityCapName());
            if (whoCountMe != null) {
                model.getEntitiesCountMe().addAll(whoCountMe);
            }
        });
    }

    public static void main(String[] args) {
        new EntityRepositoryRender().singleRender();
    }
}
