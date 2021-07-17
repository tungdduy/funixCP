package generator.models.abstracts;

import generator.models.interfaces.SeparatorContent;
import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

@Getter
@Setter
public abstract class AbstractRestModel extends AbstractUrlModel implements SeparatorContent {

    protected String
            bodyContent = "",
            importContent = "",
            bodySeparator = StringUtil.buildSeparator("BODY"),
            importSeparator = StringUtil.buildSeparator("IMPORT");

    protected String
            packagePath,
            capitalizeName;
}
