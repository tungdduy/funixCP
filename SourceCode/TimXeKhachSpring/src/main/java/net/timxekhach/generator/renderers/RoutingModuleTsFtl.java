package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.sources.RoutingModuleTsSource;
import net.timxekhach.utility.XeAppUtil;

public class RoutingModuleTsFtl extends AbstractAppUrlTemplateBuilder<RoutingModuleTsSource> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleSource(RoutingModuleTsSource source) {
        int level = source.getUrlNode().getBuilder().getLevel();
        source.setPathToFramework(XeAppUtil.getPathToFramework(level));
        source.setUrlKeyChain(source.getUrlNode().getBuilder().buildKeyChain());
        source.setCapitalizeName(source.getUrlNode().getBuilder().buildCapitalizeName());
    }
}
