package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.sources.ModuleTsSource;

public class ModuleTsFtl extends AbstractAppUrlTemplateBuilder<ModuleTsSource> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleSource(ModuleTsSource source) {
        source.setCapitalizeName(source.getUrlNode().getBuilder().buildCapitalizeName());
        source.setUrl(source.getUrlNode().getUrl());
        source.getUrlNode()
                .getChildren()
                .stream()
                .map(ModuleTsSource.Component::new)
                .forEach(source.getChildren()::add);
    }

}
