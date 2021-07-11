package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.AbstractUrlTemplateSource;

import static net.timxekhach.utility.XeAppUtil.PAGES_DIR;

@Getter
@Setter
public class ComponentTsSource extends AbstractUrlTemplateSource {
    //______________ Must be used in template
    private String
            pathToAbstract,
            pathToFramework,
            pathToI18n,
            url,
            componentName;
    //========================================

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.ts";
    }
}
