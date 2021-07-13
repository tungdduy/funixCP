package generator.app.models.abstracts;

import generator.urls.UrlNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractUrlTemplateModel extends AbstractTemplateModel {
    private UrlNode urlNode;
}
