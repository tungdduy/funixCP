package generator.java.data.repository;

import generator.abstracts.render.AbstractEntityRender;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntityRepositoryRender extends AbstractEntityRender<EntityRepositoryModel> {


    @Override
    protected void handleModel(EntityRepositoryModel model) {

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
        model.setByPkMethods(byPkMethods);
    }

}
