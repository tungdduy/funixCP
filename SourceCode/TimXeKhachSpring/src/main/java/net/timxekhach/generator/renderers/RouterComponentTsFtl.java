package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.sources.RouterComponentTsSource;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.utility.XeAppUtil;

public class RouterComponentTsFtl extends AbstractAppUrlTemplateBuilder<RouterComponentTsSource> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleSource(RouterComponentTsSource source) {
        UrlNode urlNode = source.getUrlNode();
        int level = urlNode.getBuilder().getLevel();
        source.setPathToFramework(XeAppUtil.getPathToFramework(level));
        source.setPathToI18n(XeAppUtil.getPathToI18n(level));
        source.setUrl(urlNode.getUrl());
        source.setCapitalizeName(urlNode.getBuilder().buildCapitalizeName());
        urlNode.getChildren().forEach(child -> {
            source.getChildren().add(new RouterComponentTsSource.Children(child));
        });
    }
}
