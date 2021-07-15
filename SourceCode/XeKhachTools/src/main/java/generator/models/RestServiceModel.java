package generator.models;

import generator.models.abstracts.AbstractRestModel;
import lombok.Getter;
import lombok.Setter;

import static generator.GeneratorSetup.API_OPERATION_REST_SERVICE_ROOT;

@Getter @Setter
public class RestServiceModel extends AbstractRestModel {

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_REST_SERVICE_ROOT + this.getUrlNode().getBuilder().buildCapitalizeName() + "Service.java";
    }
}