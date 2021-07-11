package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.AbstractUrlTemplateSource;

import static net.timxekhach.generator.GeneratorCenter.API_OPERATION_REST_SERVICE_ROOT;
import static net.timxekhach.utility.XeStringUtils.buildSeparator;

@Getter @Setter
public class RestServiceSource extends AbstractUrlTemplateSource {

    private final String
            importSeparator = buildSeparator("IMPORT"),
            bodySeparator = buildSeparator("BODY");

    private String
            packagePath,
            importContent,
            bodyContent,
            capitalizeName
    ;

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_REST_SERVICE_ROOT + this.getUrlNode().getBuilder().buildCapitalizeName() + "Service.java";
    }
}