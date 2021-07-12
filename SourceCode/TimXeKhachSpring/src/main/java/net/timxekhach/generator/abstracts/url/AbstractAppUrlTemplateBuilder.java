package net.timxekhach.generator.abstracts.url;

import net.timxekhach.security.model.UrlTypeEnum;

import java.util.Collections;
import java.util.List;

public abstract class AbstractAppUrlTemplateBuilder<E extends AbstractUrlTemplateSource> extends AbstractUrlTemplateBuilder<E> {

    @Override
    protected List<UrlTypeEnum> traverseUrlTypes() {
        return Collections.singletonList(UrlTypeEnum.APP);
    }
}
