package generator.java.data.repository;

import generator.GeneratorSetup;
import generator.abstracts.models.AbstractEntityModel;

@SuppressWarnings("rawtypes")
public class EntityRepositoryModel extends AbstractEntityModel {


    @Override
    public String buildRenderFilePath() {
        return GeneratorSetup.API_OPERATION_DATA_REPOSITORY_ROOT + this.entityClassName + "Repository.java";
    }
}
