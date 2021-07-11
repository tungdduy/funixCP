package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.AbstractUrlTemplateSource;
import net.timxekhach.security.model.UrlNode;

import java.util.ArrayList;
import java.util.List;

import static net.timxekhach.utility.XeAppUtil.PAGES_DIR;

@Getter
@Setter
public class RouterComponentTsSource extends AbstractUrlTemplateSource {

    private String pathToFramework, pathToI18n, url, capitalizeName;
    private List<Children> children = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.ts";
    }

    @Getter @Setter
    public static class Children {
        private String key, keyChain;
        public Children(UrlNode urlNode) {
            this.key = urlNode.getBuilder().buildKey();
            this.keyChain = urlNode.getBuilder().buildKeyChain();
        }
    }
}
