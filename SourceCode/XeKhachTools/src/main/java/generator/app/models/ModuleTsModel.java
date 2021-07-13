package generator.app.models;

import generator.app.models.abstracts.AbstractUrlTemplateModel;
import generator.urls.UrlNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static net.timxekhach.utility.XeAppUtil.PAGES_DIR;
@Getter
@Setter
public class ModuleTsModel extends AbstractUrlTemplateModel {

    private List<Component> children = new ArrayList<>();
    private String capitalizeName, url;

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".module.ts";
    }

    @Getter
    public static class Component {
        private final String componentName;
        private final String url;

        public Component(UrlNode node) {
            this.componentName = node.getBuilder().buildComponentName();
            this.url = node.getUrl();
        }
    }

}
