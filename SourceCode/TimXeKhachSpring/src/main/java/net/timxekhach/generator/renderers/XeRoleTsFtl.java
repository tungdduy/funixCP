package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractTemplateBuilder;
import net.timxekhach.generator.sources.XeRoleTsSource;

public class XeRoleTsFtl extends AbstractTemplateBuilder<XeRoleTsSource> {

    @Override
    protected void handleSource(XeRoleTsSource source) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

}
