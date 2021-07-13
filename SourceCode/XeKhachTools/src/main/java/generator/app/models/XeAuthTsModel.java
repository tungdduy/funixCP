package generator.app.models;

import generator.app.models.abstracts.AbstractTemplateModel;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.security.constant.AuthEnum;

import static net.timxekhach.utility.XeAppUtil.BUSINESS_DIR;

@Getter @Setter
public class XeAuthTsModel extends AbstractTemplateModel {

    private final AuthEnum[] auths = AuthEnum.values();

    @Override
    public String buildRenderFilePath() {
        return BUSINESS_DIR + "auth.enum.ts";
    }

}
