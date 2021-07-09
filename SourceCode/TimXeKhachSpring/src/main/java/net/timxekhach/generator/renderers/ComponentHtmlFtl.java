package net.timxekhach.generator.renderers;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.model.UrlNode;

import static net.timxekhach.utility.XeAppUtils.PAGES_DIR;

public class ComponentHtmlFtl<E extends AbstractTemplateSource> extends AbstractAppUrlTemplateBuilder<E> {

    @SuppressWarnings("unchecked")
    protected E visitUrlNode(UrlNode urlNode) {

        return (E) new AbstractTemplateSource(urlNode) {
            @Getter
            private final String name = urlNode.getBuilder().buildCapitalizeName();

            @Override
            protected String buildRenderFilePath() {
                return PAGES_DIR + urlNode.getBuilder().buildUrlChain() + "/" + urlNode.getUrl() + ".component.html";
            }
        };
    }
}
