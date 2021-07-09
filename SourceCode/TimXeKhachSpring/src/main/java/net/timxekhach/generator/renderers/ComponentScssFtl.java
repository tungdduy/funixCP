package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.model.UrlNode;

import static net.timxekhach.utility.XeAppUtils.PAGES_DIR;

public class ComponentScssFtl<E extends AbstractTemplateSource> extends AbstractAppUrlTemplateBuilder<E> {

    @SuppressWarnings("unchecked")
    protected E visitUrlNode(UrlNode urlNode) {

        return (E) new AbstractTemplateSource(urlNode) {
            @Override
            protected String buildRenderFilePath() {
                return PAGES_DIR + urlNode.getBuilder().buildUrlChain() + "/" + urlNode.getUrl() + ".component.scss";
            }
        };
    }
}
