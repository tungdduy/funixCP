package generator.models;

import generator.GeneratorSetup;
import generator.models.abstracts.AbstractEntityModel;

@SuppressWarnings("rawtypes")
public class EntityRepositoryModel extends AbstractEntityModel {
    @Override
    public String buildRenderFilePath() {
        return GeneratorSetup.API_OPERATION_DATA_REPOSITORY_ROOT + this.entityClassName + "Repository.java";
    }
}
