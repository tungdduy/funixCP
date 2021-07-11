package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;

import java.util.Map;
import java.util.Set;

import static net.timxekhach.utility.XeAppUtil.I18N_DIR;

@Getter
@Setter
public class ApiMessagesTsSource extends AbstractTemplateSource {

    //______________ Must be used in template
    private Set<Map.Entry<String, String>> messages;
    //____________________________________

    @Override
    public String buildRenderFilePath() {
        return I18N_DIR + "api-messages_vi.ts";
    }
}
