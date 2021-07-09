package net.timxekhach.generator.renderers;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.utility.XeAppUtils;

import static net.timxekhach.utility.XeAppUtils.PAGES_DIR;

public class RoutingModuleTsFtl<E extends AbstractTemplateSource> extends AbstractAppUrlTemplateBuilder<E> {

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected E visitUrlNode(UrlNode urlNode) {
        return (E) new AbstractTemplateSource(urlNode) {
            @Getter
            private String pathToFramework, urlKeyChain, capitalizeName;

            @Override
            protected void init() {
                int level = urlNode.getBuilder().getLevel();
                this.pathToFramework = XeAppUtils.getPathToFramework(level);
                this.urlKeyChain = urlNode.getBuilder().buildKeyChain();
                this.capitalizeName = urlNode.getBuilder().buildCapitalizeName();
            }

            @Override
            protected String buildRenderFilePath() {
                return PAGES_DIR + urlNode.getBuilder().buildUrlChain() + "/" + urlNode.getUrl() + "-routing.module.ts";
            }
        };
    }
}
