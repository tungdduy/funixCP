package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractApiUrlTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.model.UrlNode;

import java.util.Collections;
import java.util.List;

public class RestServiceFtl <E extends AbstractTemplateSource> extends AbstractApiUrlTemplateBuilder<E> {

    @Override
    protected E visitUrlNode(UrlNode urlNode) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<E> prepareRenderFiles() {
        return Collections.singletonList ((E) new AbstractTemplateSource() {

            @Override
            protected String buildRenderFilePath() {
                return null;
            }
        });
    }
}