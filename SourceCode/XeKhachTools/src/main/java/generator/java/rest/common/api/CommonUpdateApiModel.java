package generator.java.rest.common.api;

import data.entities.abstracts.AbstractEntity;
import generator.abstracts.models.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static generator.GeneratorSetup.API_OPERATION_REST_API_ROOT;

@Getter
@Setter
public class CommonUpdateApiModel extends AbstractModel {

    List<AbstractEntity> entities = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_REST_API_ROOT + "CommonUpdateApi.java";
    }

}
