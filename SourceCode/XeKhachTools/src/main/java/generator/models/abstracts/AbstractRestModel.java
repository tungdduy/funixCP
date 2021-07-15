package generator.models.abstracts;

import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

@Getter
@Setter
public abstract class AbstractRestModel extends AbstractUrlTemplateModel {
    protected final String
            importSeparator = StringUtil.buildSeparator("IMPORT"),
            bodySeparator = StringUtil.buildSeparator("BODY");

    protected String
            packagePath,
            importContent,
            bodyContent,
            capitalizeName;
}
