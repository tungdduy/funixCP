package net.timxekhach.generator.abstracts;

import net.timxekhach.security.model.UrlTypeEnum.UrlTypeEnum;

import java.util.Collections;
import java.util.List;

public abstract class AbstractAppUrlTemplateBuilder<E extends AbstractTemplateSource> extends AbstractUrlTemplateBuilder<E> {
    @Override
    protected List<UrlTypeEnum> traverseUrlTypes() {
        return Collections.singletonList(UrlTypeEnum.APP);
    }
}
