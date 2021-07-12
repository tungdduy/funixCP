package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractTemplateBuilder;
import net.timxekhach.generator.sources.XeAuthTsSource;

public class XeAuthTsFtl extends AbstractTemplateBuilder<XeAuthTsSource> {

    @Override
    protected void handleSource(XeAuthTsSource source) {
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }


}
