package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.AbstractUrlTemplateSource;
import net.timxekhach.utility.XeStringUtils;

import static net.timxekhach.generator.GeneratorCenter.API_OPERATION_REST_API_ROOT;

@Getter
@Setter
public class RestApiSource extends AbstractUrlTemplateSource {

    private final String
            importSeparator = XeStringUtils.buildSeparator("IMPORT"),
            bodySeparator = XeStringUtils.buildSeparator("BODY");

    private String
            packagePath,
            importContent,
            bodyContent,
            url,
            capitalizeName,
            camelName;

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_REST_API_ROOT + this.getUrlNode().getBuilder().buildCapitalizeName() + "Api.java";
    }

}
