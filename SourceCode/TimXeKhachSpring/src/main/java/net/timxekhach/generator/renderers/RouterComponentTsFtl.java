package net.timxekhach.generator.renderers;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.utility.XeAppUtils;

import static net.timxekhach.utility.XeAppUtils.PAGES_DIR;

public class RouterComponentTsFtl<E extends AbstractTemplateSource> extends AbstractAppUrlTemplateBuilder<E> {

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected E visitUrlNode(UrlNode urlNode) {
        return (E) new AbstractTemplateSource(urlNode) {
            //______________ Must be used in template
            @Getter
            private String pathToFramework, pathToI18n, url, capitalizeName;
            //____________________________________

            @Override
            protected void init() {
                int level = urlNode.getAncestors().size();
                this.pathToFramework = XeAppUtils.getPathToFramework(level);
                this.pathToI18n = XeAppUtils.getPathToI18n(level);
                this.url = urlNode.getUrl();
                this.capitalizeName = urlNode.getBuilder().buildCapitalizeName();
            }

            @Override
            protected String buildRenderFilePath() {
                return PAGES_DIR + urlNode.getBuilder().buildUrlChain() + "/" + this.url + ".component.ts";
            }
        };
    }
}
