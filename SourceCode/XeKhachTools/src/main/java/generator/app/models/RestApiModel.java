package generator.app.models;

import generator.app.models.abstracts.AbstractRestModel;
import lombok.Getter;
import lombok.Setter;

import static generator.GenerationCentral.API_OPERATION_REST_API_ROOT;

@Getter
@Setter
public class RestApiModel extends AbstractRestModel {

    private String
            url,
            camelName;

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_REST_API_ROOT + this.getUrlNode().getBuilder().buildCapitalizeName() + "Api.java";
    }

}
