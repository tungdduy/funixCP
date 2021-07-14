package generator.app.models;

import generator.app.models.abstracts.AbstractTemplateModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static util.AppUtil.FRAMEWORK_URL_DIR;

@Getter @Setter
public class UrlDeclareTsModel extends AbstractTemplateModel {

    //______________ Must be used in template
    private String contentBeforeImport;
    private String IMPORT_SEPARATOR = String.format("%s%n%s%n%s",
            "// ----------------------------------------------------------- //",
            "// ================= IMPORT TO END OF FILE =================== //",
            "// ----------------------------------------------------------- //");
    private List<UrlModel> apiUrls = new ArrayList<>();
    private List<UrlModel> appUrls = new ArrayList<>();
    //__________________________________________________________

    @Override
    public String buildRenderFilePath() {
        return FRAMEWORK_URL_DIR + "url.declare.ts";
    }

    @Getter @Setter
    public static class UrlModel {
        private String config;
        private String key;
        private final List<UrlModel> children = new ArrayList<>();
    }
}
