package generator.ts.url.declare;

import generator.abstracts.models.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static util.AppUtil.FRAMEWORK_URL_DIR;

@Getter @Setter
public class UrlDeclareTsModel extends AbstractModel {

    //______________ Must be used in template
    private String contentBeforeImport;
    private String IMPORT_SPLITTER = String.format("%s%n%s%n%s",
            "// ----------------------------------------------------------- //",
            "// ================= IMPORT TO END OF FILE =================== //",
            "// ----------------------------------------------------------- //");
    private List<Url> apiUrls = new ArrayList<>();
    private List<Url> appUrls = new ArrayList<>();
    //__________________________________________________________

    @Override
    public String buildRenderFilePath() {
        return FRAMEWORK_URL_DIR + "url.declare.ts";
    }


}
