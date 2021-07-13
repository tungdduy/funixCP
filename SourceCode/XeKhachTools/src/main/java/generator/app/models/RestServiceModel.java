package generator.app.models;

import generator.app.models.abstracts.AbstractRestModel;
import lombok.Getter;
import lombok.Setter;

import static generator.GenerationCentral.API_OPERATION_REST_SERVICE_ROOT;

@Getter @Setter
public class RestServiceModel extends AbstractRestModel {

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_REST_SERVICE_ROOT + this.getUrlNode().getBuilder().buildCapitalizeName() + "Service.java";
    }
}