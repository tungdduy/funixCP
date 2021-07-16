package generator.models;

import generator.models.abstracts.AbstractUrlModel;
import lombok.Getter;
import lombok.Setter;

import static util.AppUtil.PAGES_DIR;

@Getter
@Setter
public class RoutingModuleTsModel extends AbstractUrlModel {

    private String pathToFramework, urlKeyChain, capitalizeName;

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + "-routing.module.ts";
    }
}
