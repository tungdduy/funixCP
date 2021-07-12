package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.url.AbstractUrlTemplateSource;

import static net.timxekhach.utility.XeAppUtil.PAGES_DIR;

@Getter
@Setter
public class RoutingModuleTsSource extends AbstractUrlTemplateSource {

    private String pathToFramework, urlKeyChain, capitalizeName;

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + "-routing.module.ts";
    }
}
