package generator.java.data.mapped.angulars.model;

import generator.java.data.mapped.EntityMappedModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static util.AppUtil.FRAMEWORK_MODEL_DIR;
import static util.AppUtil.FRAMEWORK_UTIL_DIR;

@Getter @Setter
public class EntityUtilTsModel extends EntityMappedModel {
    List<EntityUtilTsModel> allModels = new ArrayList<>();
    @Override
    public String buildRenderFilePath() {
        return FRAMEWORK_UTIL_DIR + "EntityUtil.ts";
    }
}
