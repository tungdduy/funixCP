package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.rest.AbstractRestSource;

import static net.timxekhach.generator.GeneratorCenter.API_OPERATION_REST_API_ROOT;

@Getter
@Setter
public class RestApiSource extends AbstractRestSource {

    private String
            url,
            camelName;

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_REST_API_ROOT + this.getUrlNode().getBuilder().buildCapitalizeName() + "Api.java";
    }

}
