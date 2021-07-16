package generator.models;

import generator.models.abstracts.AbstractModel;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.security.constant.AuthEnum;

import static util.AppUtil.BUSINESS_DIR;

@Getter @Setter
public class XeAuthTsModel extends AbstractModel {

    private final AuthEnum[] auths = AuthEnum.values();

    @Override
    public String buildRenderFilePath() {
        return BUSINESS_DIR + "auth.enum.ts";
    }

}
