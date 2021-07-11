package net.timxekhach.generator.abstracts;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.security.model.UrlNode;
@Getter
@Setter
public abstract class AbstractUrlTemplateSource extends AbstractTemplateSource {
    private UrlNode urlNode;
}
