package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.sources.ComponentTsSource;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.utility.XeAppUtil;

public class ComponentTsFtl extends AbstractAppUrlTemplateBuilder<ComponentTsSource> {

    @Override
    protected boolean isOverrideExistingFile() {
        return false;
    }

    @Override
    protected void handleSource(ComponentTsSource source) {
        UrlNode urlNode = source.getUrlNode();
        int level = urlNode.getBuilder().getLevel();
        source.setPathToFramework(XeAppUtil.getPathToFramework(level));
        source.setPathToAbstract(XeAppUtil.getPathToAbstract(level));
        source.setPathToI18n(XeAppUtil.getPathToI18n(level));
        source.setUrl(urlNode.getUrl());
        source.setComponentName(urlNode.getBuilder().buildComponentName());
    }
}
