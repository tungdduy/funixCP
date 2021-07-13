package generator.app.models;

import generator.app.models.abstracts.AbstractUrlTemplateModel;

import static net.timxekhach.utility.XeAppUtil.PAGES_DIR;

public class ComponentScssModel extends AbstractUrlTemplateModel {
    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.scss";
    }
}
