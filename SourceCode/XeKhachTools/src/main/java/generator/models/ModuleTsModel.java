package generator.models;

import generator.models.abstracts.AbstractUrlTemplateModel;
import architect.urls.UrlNode;
import generator.models.sub.Component;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static util.AppUtil.PAGES_DIR;
@Getter
@Setter
public class ModuleTsModel extends AbstractUrlTemplateModel {

    private List<Component> children = new ArrayList<>();
    private String capitalizeName, url;

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".module.ts";
    }

}
