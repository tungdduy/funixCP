package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.url.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.sources.ComponentHtmlSource;

public class ComponentHtmlFtl extends AbstractAppUrlTemplateBuilder<ComponentHtmlSource> {
    @Override
    protected void handleSource(ComponentHtmlSource source) {
        source.setName(source.getUrlNode().getBuilder().buildCapitalizeName() + " worked!");
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }
}
