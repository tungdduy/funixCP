package net.timxekhach.generator.renderers;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.utility.XeAppUtils;

import static net.timxekhach.utility.XeAppUtils.PAGES_DIR;

public class ComponentTsFtl<E extends AbstractTemplateSource> extends AbstractAppUrlTemplateBuilder<E> {

    @SuppressWarnings("unchecked")
    protected E visitUrlNode(UrlNode urlNode) {

        return (E) new AbstractTemplateSource(urlNode) {
            //______________ Must be used in template
            @Getter
            private String
                    pathToAbstract,
                    pathToFramework,
                    pathToI18n,
                    url,
                    componentName;
            //____________________________________

            @Override
            protected void init() {
                int level = urlNode.getBuilder().getLevel();
                this.pathToFramework = XeAppUtils.getPathToFramework(level);
                this.pathToAbstract = XeAppUtils.getPathToAbstract(level);
                this.pathToI18n = XeAppUtils.getPathToI18n(level);
                this.url = urlNode.getUrl();
                this.componentName = urlNode.getBuilder().buildComponentName();
            }

            @Override
            protected String buildRenderFilePath() {
                return PAGES_DIR + urlNode.getBuilder().buildUrlChain() + "/" + this.url + ".component.ts";
            }
        };
    }
}
