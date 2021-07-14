package generator.app.models;

import generator.app.models.abstracts.AbstractUrlTemplateModel;
import generator.urls.UrlNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static util.AppUtil.PAGES_DIR;

@Getter
@Setter
public class RouterComponentTsModel extends AbstractUrlTemplateModel {

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
