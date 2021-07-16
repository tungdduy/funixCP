package generator.models;

import generator.models.abstracts.AbstractUrlModel;
import generator.models.sub.RouterChildren;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static util.AppUtil.PAGES_DIR;

@Getter
@Setter
public class RouterComponentTsModel extends AbstractUrlModel {

    private String pathToFramework, pathToI18n, url, capitalizeName;
    private List<RouterChildren> children = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.ts";
    }

}
