package generator.ts.component.router;

import generator.abstracts.models.AbstractUrlModel;
import lombok.Getter;
import lombok.Setter;

import static util.AppUtil.PAGES_DIR;

@Getter
@Setter
public class RouterComponentTsModel extends AbstractUrlModel {

    private String url, capName;

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".component.ts";
    }

}
