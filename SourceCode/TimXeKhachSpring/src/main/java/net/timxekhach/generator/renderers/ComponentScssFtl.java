package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.sources.ComponentScssSource;

public class ComponentScssFtl extends AbstractAppUrlTemplateBuilder<ComponentScssSource> {

    @Override
    protected void handleSource(ComponentScssSource source) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }
}
