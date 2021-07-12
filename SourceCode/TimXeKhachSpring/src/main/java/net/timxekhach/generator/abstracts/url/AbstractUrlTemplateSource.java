package net.timxekhach.generator.abstracts.url;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.model.UrlNode;
@Getter
@Setter
public abstract class AbstractUrlTemplateSource extends AbstractTemplateSource {
    private UrlNode urlNode;
}
