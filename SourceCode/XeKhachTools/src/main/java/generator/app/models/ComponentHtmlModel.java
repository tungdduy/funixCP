package generator.app.models;

import generator.app.models.abstracts.AbstractUrlTemplateModel;
import lombok.Getter;
import lombok.Setter;

import static net.timxekhach.utility.XeAppUtil.PAGES_DIR;
@Getter
@Setter
public class ComponentHtmlModel extends AbstractUrlTemplateModel {

    private String name;

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.html";
    }
}
