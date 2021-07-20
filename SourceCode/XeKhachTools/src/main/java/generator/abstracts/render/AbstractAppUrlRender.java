package generator.abstracts.render;

import architect.urls.UrlTypeEnum;
import generator.abstracts.models.AbstractUrlModel;

import java.util.Collections;
import java.util.List;

public abstract class AbstractAppUrlRender<E extends AbstractUrlModel> extends AbstractUrlRender<E> {

    @Override
    protected List<UrlTypeEnum> traverseUrlTypes() {
        return Collections.singletonList(UrlTypeEnum.APP);
    }
}
