package generator.models;

import generator.models.abstracts.AbstractUrlTemplateModel;

import static util.AppUtil.PAGES_DIR;

public class ComponentScssModel extends AbstractUrlTemplateModel {
    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.scss";
    }
}
