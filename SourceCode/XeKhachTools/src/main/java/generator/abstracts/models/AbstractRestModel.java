package generator.abstracts.models;

import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

@Getter
@Setter
public abstract class AbstractRestModel extends AbstractUrlModel {

    protected String
            bodyContent,
            importContent,
            bodySeparator = StringUtil.buildSeparator("BODY"),
            importSeparator = StringUtil.buildSeparator("IMPORT");

    protected String
            packagePath,
            capitalizeName;
}
