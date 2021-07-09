package net.timxekhach.generator.renderers;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.model.UrlNode;

import java.util.ArrayList;
import java.util.List;

import static net.timxekhach.utility.XeAppUtils.PAGES_DIR;

public class ModuleTsFtl<E extends AbstractTemplateSource> extends AbstractAppUrlTemplateBuilder<E> {

    @Override
    @SuppressWarnings("unchecked")
    protected E visitUrlNode(UrlNode urlNode) {
        return (E) new AbstractTemplateSource(urlNode) {

            //______________ Must be used in template
            @Getter
            private final List<Component> components = new ArrayList<>();
            @Getter
            private String capitalizeName;
            //____________________________________

            @Override
            protected String buildRenderFilePath() {
                return PAGES_DIR + urlNode.getBuilder().buildUrlChain() + "/" + urlNode.getUrl() + ".module.ts";
            }

            @Override
            protected void init() {
                this.capitalizeName = urlNode.getBuilder().buildCapitalizeName();
                urlNode.getChildren().forEach(child -> this.components.add(new Component(child)));
            }
        };
    }

    @Getter
    public static class Component {
        private final String componentName;
        private final String url;

        public Component(UrlNode node) {
            this.componentName = node.getBuilder().buildComponentName();
            this.url = node.getUrl();
        }
    }


}
