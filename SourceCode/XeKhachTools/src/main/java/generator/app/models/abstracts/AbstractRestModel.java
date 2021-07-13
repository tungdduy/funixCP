package generator.app.models.abstracts;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.utility.XeStringUtils;

@Getter
@Setter
public abstract class AbstractRestModel extends AbstractUrlTemplateModel {
    protected final String
            importSeparator = XeStringUtils.buildSeparator("IMPORT"),
            bodySeparator = XeStringUtils.buildSeparator("BODY");

    protected String
            packagePath,
            importContent,
            bodyContent,
            capitalizeName;
}
