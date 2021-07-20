package generator.models;

import generator.models.abstracts.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

import static util.AppUtil.I18N_DIR;

@Getter
@Setter
public class ApiMessagesTsModel extends AbstractModel {

    //______________ Must be used in template
    private Set<Map.Entry<String, String>> messages;
    //____________________________________

    @Override
    public String buildRenderFilePath() {
        return I18N_DIR + "api-messages_vi.ts";
    }
}
