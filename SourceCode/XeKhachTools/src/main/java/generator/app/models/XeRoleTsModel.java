package generator.app.models;

import generator.app.models.abstracts.AbstractTemplateModel;
import lombok.Getter;
import net.timxekhach.security.constant.RoleEnum;

import static net.timxekhach.utility.XeAppUtil.BUSINESS_DIR;

@Getter
public class XeRoleTsModel extends AbstractTemplateModel {

    private final RoleEnum[] roles = RoleEnum.values();

    @Override
    public String buildRenderFilePath() {
        return BUSINESS_DIR + "xe.role.ts";
    }
}
