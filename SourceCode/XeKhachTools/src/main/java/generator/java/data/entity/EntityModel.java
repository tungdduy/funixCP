package generator.java.data.entity;

import generator.abstracts.models.AbstractEntityModel;
import lombok.Getter;
import lombok.Setter;

import static generator.GeneratorSetup.API_OPERATION_DATA_ENTITY_ROOT;

@SuppressWarnings("all") @Getter @Setter
public class EntityModel extends AbstractEntityModel {

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_ENTITY_ROOT + this.entityClassName + ".java";
    }

}
