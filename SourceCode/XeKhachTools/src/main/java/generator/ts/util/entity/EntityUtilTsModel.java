package generator.ts.util.entity;

import data.entities.abstracts.AbstractEntity;
import generator.abstracts.models.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static generator.GeneratorSetup.API_OPERATION_REST_SERVICE_ROOT;
import static util.AppUtil.FRAMEWORK_UTIL_DIR;

@Getter
@Setter
public class EntityUtilTsModel extends AbstractModel {

    List<AbstractEntity> entities = new ArrayList<>();

    @Override
    public void prepareSeparator() {
        separator("newEntityByDefiner");
        separator("aboveMainEntityId");
        separator("belowMainEntityId");
        separator("entityCache");
    }

    @Override
    public String buildRenderFilePath() {
        return FRAMEWORK_UTIL_DIR + "entity.util.ts";
    }

}
