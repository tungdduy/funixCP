package generator.models.abstracts;

import architect.urls.UrlNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractUrlTemplateModel extends AbstractTemplateModel {
    private UrlNode urlNode;
}
