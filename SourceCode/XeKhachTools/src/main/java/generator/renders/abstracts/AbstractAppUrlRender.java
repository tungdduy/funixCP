package generator.renders.abstracts;

import generator.models.abstracts.AbstractUrlModel;
import architect.urls.UrlTypeEnum;

import java.util.Collections;
import java.util.List;

public abstract class AbstractAppUrlRender<E extends AbstractUrlModel> extends AbstractUrlRender<E> {

    @Override
    protected List<UrlTypeEnum> traverseUrlTypes() {
        return Collections.singletonList(UrlTypeEnum.APP);
    }
}
