package generator.models;

import generator.models.abstracts.AbstractUrlTemplateModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

import static util.AppUtil.FRAMEWORK_URL_DIR;

@Getter
@Setter
public class UrlImportTsModel extends AbstractUrlTemplateModel {

    private Set<Map.Entry<String, String>> urlImports;

    @Override
    public String buildRenderFilePath() {
        return FRAMEWORK_URL_DIR + "url.import.ts";
    }
}
