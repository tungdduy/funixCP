package generator.html.component;

import generator.abstracts.models.AbstractUrlModel;
import lombok.Getter;
import lombok.Setter;

import static util.AppUtil.PAGES_DIR;
@Getter
@Setter
public class ComponentHtmlModel extends AbstractUrlModel {

    private String name;

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.html";
    }
}
