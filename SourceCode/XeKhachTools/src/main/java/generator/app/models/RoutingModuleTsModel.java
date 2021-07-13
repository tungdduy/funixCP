package generator.app.models;

import generator.app.models.abstracts.AbstractUrlTemplateModel;
import lombok.Getter;
import lombok.Setter;

import static net.timxekhach.utility.XeAppUtil.PAGES_DIR;

@Getter
@Setter
public class RoutingModuleTsModel extends AbstractUrlTemplateModel {

    private String pathToFramework, urlKeyChain, capitalizeName;

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + "-routing.module.ts";
    }
}
