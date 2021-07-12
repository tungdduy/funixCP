package net.timxekhach.generator.abstracts.rest;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.url.AbstractUrlTemplateSource;
import net.timxekhach.utility.XeStringUtils;

@Getter
@Setter
public abstract class AbstractRestSource extends AbstractUrlTemplateSource {
    protected final String
            importSeparator = XeStringUtils.buildSeparator("IMPORT"),
            bodySeparator = XeStringUtils.buildSeparator("BODY");

    protected String
            packagePath,
            importContent,
            bodyContent,
            capitalizeName;
}
