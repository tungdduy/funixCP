package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.AbstractUrlTemplateSource;

import java.util.Map;
import java.util.Set;

import static net.timxekhach.utility.XeAppUtil.FRAMEWORK_URL_DIR;

@Getter
@Setter
public class UrlImportTsSource extends AbstractUrlTemplateSource {

    private Set<Map.Entry<String, String>> urlImports;

    @Override
    public String buildRenderFilePath() {
        return FRAMEWORK_URL_DIR + "url.import.ts";
    }
}
