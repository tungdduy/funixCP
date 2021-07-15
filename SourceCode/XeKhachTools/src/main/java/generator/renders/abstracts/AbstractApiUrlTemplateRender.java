package generator.renders.abstracts;

import generator.models.abstracts.AbstractUrlTemplateModel;
import architect.urls.UrlTypeEnum;

import java.util.Collections;
import java.util.List;

public abstract class AbstractApiUrlTemplateRender<E extends AbstractUrlTemplateModel> extends AbstractUrlTemplateRender<E> {

    @Override
    protected List<UrlTypeEnum> traverseUrlTypes() {
        return Collections.singletonList(UrlTypeEnum.API);
    }
}