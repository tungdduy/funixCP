package generator.models;

import generator.models.abstracts.AbstractUrlModel;

import static util.AppUtil.PAGES_DIR;

public class ComponentScssModel extends AbstractUrlModel {
    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.scss";
    }
}
