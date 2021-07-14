package generator.app.models;

import generator.app.models.abstracts.AbstractUrlTemplateModel;
import lombok.Getter;
import lombok.Setter;

import static util.AppUtil.PAGES_DIR;

@Getter
@Setter
public class ComponentTsModel extends AbstractUrlTemplateModel {
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
