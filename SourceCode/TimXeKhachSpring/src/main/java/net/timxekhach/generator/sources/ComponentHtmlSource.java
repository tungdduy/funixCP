package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.url.AbstractUrlTemplateSource;

import static net.timxekhach.utility.XeAppUtil.PAGES_DIR;
@Getter
@Setter
public class ComponentHtmlSource extends AbstractUrlTemplateSource {

    private String name;

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.html";
    }
}
