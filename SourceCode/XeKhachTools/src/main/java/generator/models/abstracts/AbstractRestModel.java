package generator.models.abstracts;

import generator.models.interfaces.BodyImportContent;
import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

@Getter
@Setter
public abstract class AbstractRestModel extends AbstractUrlModel implements BodyImportContent {

    protected String
            bodyContent = "",
            importContent = "",
            bodySeparator = StringUtil.buildSeparator("BODY"),
            importSeparator = StringUtil.buildSeparator("IMPORT");

    protected String
            packagePath,
            capitalizeName;
}
