package generator.models;

import generator.models.abstracts.AbstractUrlModel;
import generator.models.sub.Component;
import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static util.AppUtil.PAGES_DIR;
@Getter
@Setter
public class ModuleTsModel extends AbstractUrlModel {

    private List<Component> children = new ArrayList<>();
    private String capitalizeName, url;

    String
            headerImportSeparator = StringUtil.buildSeparator("HEADER_IMPORT"),
            moduleImportSeparator = StringUtil.buildSeparator("MODULE_IMPORT"),
            headerImportContent, moduleImportContent;




    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".module.ts";
    }

}
