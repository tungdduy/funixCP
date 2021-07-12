package net.timxekhach.generator.sources;

import net.timxekhach.generator.abstracts.url.AbstractUrlTemplateSource;

import static net.timxekhach.utility.XeAppUtil.PAGES_DIR;

public class ComponentScssSource extends AbstractUrlTemplateSource {
    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.scss";
    }
}
