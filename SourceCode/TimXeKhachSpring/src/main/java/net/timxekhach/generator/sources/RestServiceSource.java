package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.rest.AbstractRestSource;

import static net.timxekhach.generator.GeneratorCenter.API_OPERATION_REST_SERVICE_ROOT;

@Getter @Setter
public class RestServiceSource extends AbstractRestSource {

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_REST_SERVICE_ROOT + this.getUrlNode().getBuilder().buildCapitalizeName() + "Service.java";
    }
}