package generator.models;

import generator.models.abstracts.AbstractModel;
import lombok.Getter;
import net.timxekhach.security.constant.RoleEnum;

import static util.AppUtil.BUSINESS_DIR;

@Getter
public class XeRoleTsModel extends AbstractModel {

    private final RoleEnum[] roles = RoleEnum.values();

    @Override
    public String buildRenderFilePath() {
        return BUSINESS_DIR + "xe.role.ts";
    }
}
